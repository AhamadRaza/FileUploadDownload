package com.fileUploadDownload.FileUploadDownload.controller;

import com.fileUploadDownload.FileUploadDownload.model.User;
import com.fileUploadDownload.FileUploadDownload.model.UserFiles;
import com.fileUploadDownload.FileUploadDownload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String users(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("user",new User());
        model.addAttribute("userfiles",new ArrayList<UserFiles>());
        model.addAttribute("isAdd",true);
        return "view/user";
    }

}