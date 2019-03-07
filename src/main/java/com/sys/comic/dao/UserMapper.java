package com.sys.comic.dao;

import com.sys.comic.entity.User;
import java.util.List;

public interface UserMapper {

    List<User> selectAll();

    User getByUid(long uid);

    User getByUsername(String username);

    int insert(User user);


}
