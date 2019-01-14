package codesquad.controller;

import codesquad.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new LinkedList<>();

    @PostMapping("/user/create")
    public String create(User user){
        users.add(user);
        System.out.println(user);
        System.out.println("List size : " + users.size());
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model){
        model.addAttribute("users", users);
        return "list";
    }

}
