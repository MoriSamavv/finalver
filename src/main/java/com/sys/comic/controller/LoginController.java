package com.sys.comic.controller;

import com.sys.comic.entity.User;
import com.sys.comic.service.UserService;
import com.sys.comic.util.MD5Util;
import com.sys.comic.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/loading")
public class LoginController {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    private JsonView login(String username, String password){
        String tem = MD5Util.convertMD5(password);
        System.out.println("1:"+tem);
        System.out.println("2:"+MD5Util.convertMD5(tem));

        User user = userService.getByUsername(username);
        if(user != null){
            if(password.equals(user.getPassword())){
                return new JsonView(user);
            }else{
                return new JsonView("201","密码错误");
            }

        }
        return new JsonView("203","用户不存在");
    }

    @ResponseBody
    @RequestMapping("/signup")
    private JsonView signup(String username, String password){
        User user = userService.getByUsername(username);
        if(user == null){
            userService.add(username,password);
            return new JsonView(true);
        }
        return new JsonView("204","用户名已存在");
    }
}
