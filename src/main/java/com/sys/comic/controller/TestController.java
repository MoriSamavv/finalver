package com.sys.comic.controller;

import com.sys.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    private String index(){
        userService.print();
        return "Hello World222!";
    }

}
