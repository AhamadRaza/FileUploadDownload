package com.fileUploadDownload.FileUploadDownload.service.impl;

import com.fileUploadDownload.FileUploadDownload.model.User;
import com.fileUploadDownload.FileUploadDownload.repository.UserRepository;
import com.fileUploadDownload.FileUploadDownload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}