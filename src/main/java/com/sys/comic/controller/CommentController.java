package com.sys.comic.controller;

import com.sys.comic.entity.User;
import com.sys.comic.entity.vo.CommentVO;
import com.sys.comic.service.CommentService;
import com.sys.comic.service.UserService;
import com.sys.comic.view.JsonView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/comment")
public class CommentController {

    private Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/createComment")
    private JsonView createComment(String username, Long cid, String content){
        User user = userService.getByUsername(username);
        boolean result = commentService.add(cid, user.getUid(), content);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/getCommentList")
    private JsonView getCommentList(Long cid){
        List<CommentVO> commentVOS = commentService.getCommentVOList(cid);
        return new JsonView(commentVOS);
    }
}
