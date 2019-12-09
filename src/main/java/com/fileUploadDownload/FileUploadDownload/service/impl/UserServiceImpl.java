package com.fileUploadDownload.FileUploadDownload.service.impl;
import com.fileUploadDownload.FileUploadDownload.model.User;
import com.fileUploadDownload.FileUploadDownload.model.UserFiles;
import com.fileUploadDownload.FileUploadDownload.repository.UserFilesRepository;
import com.fileUploadDownload.FileUploadDownload.repository.UserRepository;
import com.fileUploadDownload.FileUploadDownload.service.UploadPathService;
import com.fileUploadDownload.FileUploadDownload.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired private UserRepository userRepository;
    @Autowired private UploadPathService uploadPathService;
    @Autowired private UserFilesRepository userFilesRepository;
    @Autowired private ServletContext context;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) {
        user.setCreateDate(new Date());
        User dbUser = userRepository.save(user);
        if(dbUser!=null && user.getFile()!=null && user.getFile().size()>0){
            for(MultipartFile file : user.getFile()){
                if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
                    String fileName = file.getOriginalFilename();
                    String modifiedFileName = FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
                    File storeFile = uploadPathService.getFilePath(modifiedFileName, "images");
                    if(storeFile!=null){
                        try {
                            FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    UserFiles files = new UserFiles();
                    files.setFileExtension(FilenameUtils.getExtension(fileName));
                    files.setFileName(fileName);
                    files.setModifiedFileName(modifiedFileName);
                    files.setUser(dbUser);
                    userFilesRepository.save(files);
                }
            }
        }
        return dbUser;
    }

    @Override
    public User findById(Long userId) {
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        else
            return null;
    }

    @Override
    public List<UserFiles> findfilesByUserId(Long userId) {
        return userFilesRepository.findfilesByUserId(userId);
    }

    @Override
    public User update(User user) {
        user.setUpdateDate(new Date());
        User dbUser = userRepository.save(user);
        if(user!=null && user.getRemoveImages()!=null && user.getRemoveImages().size()>0){
            userFilesRepository.deletefilesByUserIdAndImagesNames(user.getId(),user.getRemoveImages());
            for(String file : user.getRemoveImages()){
                File dbFile = new File(context.getRealPath("/images/"+File.separator+file));
                if(dbFile.exists()){
                    dbFile.delete();
                }
            }
        }
        if(dbUser!=null && user.getFile()!=null && user.getFile().size()>0){
            for(MultipartFile file : user.getFile()) {
                if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
                    String fileName = file.getOriginalFilename();
                    String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
                    File storeFile = uploadPathService.getFilePath(modifiedFileName, "images");
                    if (storeFile != null) {
                        try {
                            FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    UserFiles files = new UserFiles();
                    files.setFileExtension(FilenameUtils.getExtension(fileName));
                    files.setFileName(fileName);
                    files.setModifiedFileName(modifiedFileName);
                    files.setUser(dbUser);
                    userFilesRepository.save(files);
                }
            }
        }
        return dbUser;
    }

    @Override
    public void deleteFilesByUserId(Long userId) {
        List<UserFiles> userFiles = userFilesRepository.findfilesByUserId(userId);
        if(userFiles!=null && userFiles.size()>0){
            for(UserFiles dbFiles : userFiles){
                File dbStoreFile = new File(context.getRealPath("/images/"+File.separator+dbFiles.getModifiedFileName()));
                if(dbStoreFile.exists()){
                    dbStoreFile.delete();
                }
            }
            userFilesRepository.deleteFilesByUserId(userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}