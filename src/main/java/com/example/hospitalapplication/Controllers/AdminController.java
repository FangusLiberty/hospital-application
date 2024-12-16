package com.example.hospitalapplication.Controllers;

import com.example.hospitalapplication.Entity.User;
import com.example.hospitalapplication.Exception.UserNotFoundException;
import com.example.hospitalapplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping()
    public String admin(){
        return "admin";
    }

    @GetMapping("/settings")
    public String adminSettings(Model model){
        model.addAttribute("users",userService.findeAll());
        return "admin-settings";
    }
    @GetMapping("/{user_id}/edit-role")
    public String editRolePage(Model model, @PathVariable Long user_id) throws UserNotFoundException {
        User user = userService.findById(user_id);
        model.addAttribute("user",user);
        return "edit-role";
    }
    @PostMapping("/{user_id}/edit-role")
    public String editRole(@RequestParam String role, @PathVariable Long user_id) throws UserNotFoundException {
        userService.updateUser(role, user_id);
        return "redirect:/";
    }
    @GetMapping("/add-user")
    public String addUserPage(){
        return "registration";
    }
    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user){
        userService.createUser(user);
        return "redirect:/admin";
    }
    @PostMapping("/{user_id}/delete")
    public String deleteUser(@PathVariable Long user_id) throws UserNotFoundException {
        userService.deleteUser(user_id);
        return "redirect:/admin";
    }

}
