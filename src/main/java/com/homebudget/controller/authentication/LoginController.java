package com.homebudget.controller.authentication;

import com.homebudget.domain.authentication.User;
import com.homebudget.service.authentication.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/auth")
    public ResponseEntity<Map> authenticate(@RequestBody User user){

        return loginService.authenticate(user);
    }

    @PostMapping("/auth/create")
    public ResponseEntity<Void> createUser(@RequestBody User user){

        return loginService.create(user);
    }
}
