package com.sys.comic.controller;

import com.sys.comic.entity.Chapter;
import com.sys.comic.entity.vo.ChapterVO;
import com.sys.comic.entity.vo.ChapterVerifyVO;
import com.sys.comic.service.ChapterService;
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
@RequestMapping(value = "/chapter")
public class ChapterController {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ChapterService chapterService;

    @ResponseBody
    @RequestMapping("/createChapter")
    private JsonView createChapter(Long cid, Integer section, String title){
        boolean result = chapterService.add(cid, section, title);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/getChapterList")
    private JsonView getChapterList(Long cid){
        List<Chapter> chapters = chapterService.getChapterList(cid);
        return new JsonView(chapters);
    }

    @ResponseBody
    @RequestMapping("/delChapter")
    private JsonView delChapter(Long chapterId){
        boolean result = chapterService.del(chapterId);
        return new JsonView(result);
    }

    @ResponseBody
    @RequestMapping("/getChapterVOList")
    private JsonView getChapterVOList(Long cid){
        List<ChapterVO> chapterVOS = chapterService.getChapterVOList(cid);
        return new JsonView(chapterVOS);
    }

    @ResponseBody
    @RequestMapping("/getChapterVerifyVOList")
    private JsonView getChapterVerifyVOList(){
        List<ChapterVerifyVO> chapterVerifyVOS = chapterService.getChapterVerifyVOList();
        return new JsonView(chapterVerifyVOS);
    }

    @ResponseBody
    @RequestMapping("/setChapterVerify")
    private JsonView setChapterVerify(Long chapterId, Integer verify){
        boolean result = chapterService.setVerify(chapterId, verify);
        return new JsonView(result);
    }
}
