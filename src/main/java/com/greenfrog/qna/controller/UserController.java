package com.greenfrog.qna.controller;

import com.greenfrog.qna.domain.User;
import com.greenfrog.qna.dto.UserUpdateDTO;
import com.greenfrog.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String signUp(User user) {
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, UserUpdateDTO userUpdateDTO) {
        if (userService.update(id, userUpdateDTO)) {
            return "redirect:/users";
        }
        return "redirect:/users/" + id + "/form";
    }
}
