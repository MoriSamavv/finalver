package com.sys.comic.controller;

import com.sys.comic.entity.User;
import com.sys.comic.entity.vo.ComicInnerCoverVO;
import com.sys.comic.entity.vo.ComicManagementVO;
import com.sys.comic.entity.vo.ComicOuterCoverVO;
import com.sys.comic.service.ComicService;
import com.sys.comic.service.UserService;
import com.sys.comic.view.JsonView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/comic")
public class ComicController {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ComicService comicService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/createComic")
    private JsonView createComic(String title, String author, String description){
        logger.info(title);
        boolean result = comicService.add(title, author, description);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/getAllComic")
    private JsonView getAllComic(){
        List<ComicManagementVO> comicManagementVOS = comicService.getComicManagementVOList();
        return new JsonView(comicManagementVOS);
    }

    @ResponseBody
    @RequestMapping("/delComic")
    private JsonView delComic(Long cid){
        boolean result = comicService.del(cid);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/uploadCover")
    private JsonView uploadCover(Long cid, String type, HttpServletRequest request) throws IOException {
        logger.info("cover:----"+cid+"---"+type);
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("fileName:"+originalFilename);
        Date date = new Date();
        long time = date.getTime();
        String fileName = cid+"-"+time+"-"+originalFilename;
        String path="C:\\Users\\Administrator\\IdeaProjects\\comic\\vue-test\\comic\\public\\"+fileName;
        System.out.println("path:" + path);
        File newFile = new File(path);
        multipartFile.transferTo(newFile);
        boolean result = comicService.updateCoverUrl(cid,"/"+fileName, type);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/getOuterCover")
    private JsonView getOuterCover(){
        List<ComicOuterCoverVO> comicOuterCoverVOS = comicService.getComicOuterCoverVOList();
        return new JsonView(comicOuterCoverVOS);
    }

    @ResponseBody
    @RequestMapping("/getInnerCover")
    private JsonView getInnerCover(String username, Long cid){
        User user = userService.getByUsername(username);
        logger.info(user.getUsername());
        ComicInnerCoverVO comicInnerCoverVO = comicService.getComicInnerCoverVO(user.getUid(), cid);
        return new JsonView(comicInnerCoverVO);
    }
}
