package com.example.hospitalapplication.Controllers;

import com.example.hospitalapplication.Entity.User;
import com.example.hospitalapplication.Exception.DoctorNotFoundException;
import com.example.hospitalapplication.Service.DoctorService;
import com.example.hospitalapplication.Service.SpecialtyService;
import com.example.hospitalapplication.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final SpecialtyService specialtyService;
    private final UserService userService;
    private final DoctorService doctorService;

    @GetMapping("/")
    public String allSpecialty(Model model){
        model.addAttribute("specialty",specialtyService.findAll());
        return "main";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/registration")
    public String createUser(@ModelAttribute User user){
        System.out.println(user);
        userService.createUser(user);
        return "redirect:/login";
    }
    @GetMapping("/{spec_id}/doctors")
    public String getDoctorsOfScpec(Model model, @PathVariable Long spec_id) throws DoctorNotFoundException {
        model.addAttribute("doctors",doctorService.getAllBySpecialtuId(spec_id));
        return "doctors";
    }



}
