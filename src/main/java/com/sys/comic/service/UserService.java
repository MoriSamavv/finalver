package com.sys.comic.service;

import com.sys.comic.entity.User;

public interface UserService {

    void print();

    User getByUsername(String username);

    boolean add(String username, String password);
}
