package com.cybersoft.demospringsecurity.controller;


import com.cybersoft.demospringsecurity.utils.JWTHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired

    AuthenticationManager authenticationManager;

    @Autowired
    JWTHelperUtils jwtHelperUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password){

        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username,password);
        // thực thi chứng thực sử dụng CustomAuthenProvider
        authenticationManager.authenticate(user);

        // chứng thực thành công thì code sẽ chạy tiếp và tạo token
        String token = jwtHelperUtils.generateToken(username);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


}
