package com.fileUploadDownload.FileUploadDownload.service;

import com.fileUploadDownload.FileUploadDownload.model.User;
import com.fileUploadDownload.FileUploadDownload.model.UserFiles;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User save(User user);
    public User findById(Long userId);
    public List<UserFiles> findfilesByUserId(Long userId);
    public User update(User user);
}