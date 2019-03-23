package com.sys.comic.service;

import com.sys.comic.entity.vo.CommentVO;

import java.util.List;

public interface CommentService {

    boolean add(long cid, long uid, String content);

    List<CommentVO> getCommentVOList(long cid);
}
