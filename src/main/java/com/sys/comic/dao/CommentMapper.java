package com.sys.comic.dao;

import com.sys.comic.entity.Comment;

import java.util.List;

public interface CommentMapper {

    int insert(Comment comment);

    List<Comment> getByCid(long cid);
}
