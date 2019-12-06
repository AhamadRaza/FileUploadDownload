package com.fileUploadDownload.FileUploadDownload.controller;

import com.fileUploadDownload.FileUploadDownload.model.User;
import com.fileUploadDownload.FileUploadDownload.model.UserFiles;
import com.fileUploadDownload.FileUploadDownload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping(value = "/save")
    public String create(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model){
        User dbUser = userService.save(user);
        if(dbUser!=null){
            redirectAttributes.addFlashAttribute("successmessage", "user saved successfully");
            return "redirect:/";
        }
        else
        model.addAttribute("errormessage", "user is not saved! Please try again!");
        model.addAttribute("user", user);
        return "view/user";
    }
    @GetMapping(value = "/edituser/{userId}")
    public String edituser(@PathVariable Long userId, Model model){
        User user = userService.findById(userId);
        List<UserFiles> userFiles = userService.findfilesByUserId(userId);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("user", user);
        model.addAttribute("userfiles", userFiles);
        model.addAttribute("isAdd", false);
        return "view/user";
    }
    @PostMapping(value = "/update")
    public String update(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model){
        User dbUser = userService.update(user);
        if(dbUser!=null){
            redirectAttributes.addFlashAttribute("successmessage", "user updated successfully");
            return "redirect:/";
        }
        else
            model.addAttribute("errormessage", "user is not update! Please try again!");
        model.addAttribute("user", user);
        return "view/user";
}