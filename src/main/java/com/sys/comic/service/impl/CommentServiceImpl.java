package com.sys.comic.service.impl;

import com.sys.comic.dao.CommentMapper;
import com.sys.comic.dao.UserMapper;
import com.sys.comic.entity.Comment;
import com.sys.comic.entity.User;
import com.sys.comic.entity.vo.CommentVO;
import com.sys.comic.service.CommentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean add(long cid, long uid, String content) {
        Comment comment = new Comment();
        comment.setCid(cid);
        comment.setUid(uid);
        comment.setContent(content);
        comment.setPosttime(new Date());
        int result = commentMapper.insert(comment);
        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public List<CommentVO> getCommentVOList(long cid) {
        List<Comment> comments = commentMapper.getByCid(cid);
        List<CommentVO> commentVOS = new ArrayList<CommentVO>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Comment comment : comments){
            CommentVO commentVO = new CommentVO();
            commentVO.setCommentid(comment.getCommentid());
            User user = userMapper.getByUid(comment.getUid());
            commentVO.setNickname(user.getNickname());
            commentVO.setContent(comment.getContent());
            commentVO.setPosttime(formatter.format(comment.getPosttime()));
            commentVOS.add(commentVO);
        }
        return commentVOS;
    }
}
