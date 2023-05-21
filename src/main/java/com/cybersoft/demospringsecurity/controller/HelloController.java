package com.cybersoft.demospringsecurity.controller;
import com.cybersoft.demospringsecurity.utils.JWTHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    JWTHelperUtils jwtHelperUtils;

    @GetMapping("")
    public ResponseEntity<?> index(){
        String token = jwtHelperUtils.generateToken("Cybersoft");
        String data = jwtHelperUtils.validToken(token);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/loichao")
    public ResponseEntity<?> indexLoiChao(){

        return new ResponseEntity<>("Hello Page loi chao", HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> index(@PathVariable String name ){

        return new ResponseEntity<>("Hello Page " + name, HttpStatus.OK);
    }


}
