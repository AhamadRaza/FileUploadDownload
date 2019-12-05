package com.fileUploadDownload.FileUploadDownload.repository;

import com.fileUploadDownload.FileUploadDownload.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>{
}