package com.sys.comic.controller;

import com.sys.comic.entity.User;
import com.sys.comic.entity.vo.StarVO;
import com.sys.comic.service.StarService;
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
@RequestMapping(value = "/star")
public class StarController {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private StarService starService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/setLike")
    private JsonView setLike(String username, Long cid, Boolean like){
        logger.info(username+"-"+cid+"-"+like);
        User user = userService.getByUsername(username);
        logger.info(user.getUid());
        boolean result = starService.updateLike(user.getUid(), cid, like);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/getStarList")
    private JsonView getStarList(String username){
        User user = userService.getByUsername(username);
        List<StarVO> starVOS = starService.getStarVOList(user.getUid());
        return new JsonView(starVOS);
    }

    @ResponseBody
    @RequestMapping("/setChapter")
    private JsonView setChapter(String username, Long cid, Long chapterId){
        User user = userService.getByUsername(username);
        logger.info(user.getUid());
        boolean result = starService.updateChapterId(user.getUid(), cid, chapterId);
        return new JsonView(result);
    }
}
