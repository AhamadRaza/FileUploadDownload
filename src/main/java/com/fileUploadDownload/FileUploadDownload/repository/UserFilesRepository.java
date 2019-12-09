package com.fileUploadDownload.FileUploadDownload.repository;

import com.fileUploadDownload.FileUploadDownload.model.UserFiles;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFilesRepository extends CrudRepository<UserFiles,Long>{
    @Query("select f from UserFiles as f where f.user.id=?1")
   public List<UserFiles> findfilesByUserId(Long userId);

    @Modifying
    @Query("delete  from UserFiles as f where f.user.id=?1 and f.modifiedFileName in (?2)")
    public void deletefilesByUserIdAndImagesNames(Long id, List<String> removeImages);

    @Modifying
    @Query("delete  from UserFiles as f where f.user.id=?1")
    public void deleteFilesByUserId(Long userId);
}