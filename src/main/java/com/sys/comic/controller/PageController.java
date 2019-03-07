package com.sys.comic.controller;

import com.sys.comic.entity.Page;
import com.sys.comic.service.PageService;
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
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/page")
public class PageController {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private PageService pageService;

    @ResponseBody
    @RequestMapping("/uploadPage")
    private JsonView uploadPage(Long chapterId, HttpServletRequest request) throws IOException {
        logger.info("image:----"+chapterId);
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = chapterId+"-"+originalFilename;
        logger.info("fileName:"+fileName);
        String path="C:\\Users\\Administrator\\IdeaProjects\\comic\\vue-test\\comic\\public\\"+fileName;
        System.out.println("path:" + path);
        File newFile = new File(path);
        multipartFile.transferTo(newFile);
        boolean result = pageService.add(chapterId, "/"+fileName);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/readComic")
    private JsonView readComic(Long chapterId) {
        List<Page> pageList = pageService.getPageList(chapterId);
        return new JsonView(pageList);
    }
}
