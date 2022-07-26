package com.project.MyShop.services;

import com.project.MyShop.models.Role;
import com.project.MyShop.models.User;
import com.project.MyShop.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    public boolean createUser(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.USER);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Привет %s! \n" +
                            " Добро пожаловать в MyMother. " +
                            " Пожалуйста, активируйте аккаунт по ссылке: <a href='http://localhost:5555/activate/%s'>link</a>",
                     user.getName(),
                     user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activeUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if(user != null) {
            user.setActivationCode("active");
            user.setActive(true);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    public List<User> userList() {
        return userRepo.findAll();
    }

    public void ban(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
            }else {
                user.setActive(true);
            }
        }
        userRepo.save(user);
    }

    public void changeRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays
                .stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepo.findByEmail(principal.getName());
    }
}
