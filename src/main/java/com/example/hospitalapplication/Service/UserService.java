package com.example.hospitalapplication.Service;

import com.example.hospitalapplication.Entity.User;
import com.example.hospitalapplication.Entity.enums.Role;
import com.example.hospitalapplication.Exception.UserNotFoundException;

import com.example.hospitalapplication.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(User user){
        String email = user.getEmail();
        if(userRepository.findByEmail(email)!=null) return false;
        System.out.println(user);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }

    public List<User> findeAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) throws UserNotFoundException {
        Optional<User> opt_user = userRepository.findById(userId);
        if(opt_user.isPresent()){
            return opt_user.get();
        }
        else
            throw new UserNotFoundException("user with id '"+userId+"' not found");
    }

    public void updateUser(String role, Long userId) throws UserNotFoundException {
       Optional<User> opt_user = userRepository.findById(userId);
       if(opt_user.isPresent()){
           if(role.equals("ADMIN")) {
               opt_user.get().getRoles().add(Role.ROLE_ADMIN);
           }
           if(role.equals("REGISTRATOR")) {
               opt_user.get().getRoles().add(Role.ROLE_REGISTRATOR);
           }
           userRepository.save(opt_user.get());
       }
       else
           throw new UserNotFoundException("user with id '"+userId+"' not found");

    }

    public void deleteUser(Long userId) throws UserNotFoundException {
        Optional<User> opt_user = userRepository.findById(userId);
        if(opt_user.isPresent()){
            userRepository.delete(opt_user.get());
        }
        else
            throw new UserNotFoundException("user with id '"+userId+"'not found ");


    }
}