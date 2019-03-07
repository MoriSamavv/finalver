package com.sys.comic.service.impl;

import com.sys.comic.dao.UserMapper;
import com.sys.comic.entity.User;
import com.sys.comic.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public void print() {
        List<User> list = new ArrayList();
        list = userMapper.selectAll();
        for(User user : list){
            System.out.println(user.getUsername());
        }
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public boolean add(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(username);
        user.setPassword(password);
        int result = userMapper.insert(user);
        logger.info("result:"+result);
        if(result == 1){
            return true;
        }
        return false;
    }
}
