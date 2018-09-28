package com.sinners.demo.service;

import com.sinners.demo.repository.UserRepository;
import com.sinners.demo.user.Role;
import com.sinners.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private static final String LINK = "http://localhost:8080/activation/";
    private static final String ACTIVATION_CODE = "Activation code";
    private static final String WELCOME = "Hey, %s! \n" +
            "Welcome to sinners app, share your sins with other guys and have fun!\n" +
            "To activate your account, go to link: %s%s";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MailService mailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(WELCOME, user.getUsername(), LINK, user.getActivationCode());
            mailService.sendEmail(user.getEmail(), ACTIVATION_CODE, message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return false;
    }
}
