package com.sys.comic.service.impl;

import com.sys.comic.dao.ChapterMapper;
import com.sys.comic.dao.ComicMapper;
import com.sys.comic.entity.Chapter;
import com.sys.comic.entity.Comic;
import com.sys.comic.entity.vo.ChapterVO;
import com.sys.comic.entity.vo.ChapterVerifyVO;
import com.sys.comic.service.ChapterService;
import com.sys.comic.service.ComicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private ComicService comicService;
    @Autowired
    private ComicMapper comicMapper;

    @Override
    public boolean add(Long cid, Integer section, String title) {
        Chapter chapter = new Chapter();
        chapter.setCid(cid);
        chapter.setSection(section);
        chapter.setTitle(title);
        chapter.setPosttime(new Date());
        int result = chapterMapper.insert(chapter);
        if(result == 1){
            comicService.updateLatest(cid);
            return true;
        }
        return false;
    }

    @Override
    public List<Chapter> getChapterList(Long cid) {
        return chapterMapper.selectInManagementPage(cid);
    }

    @Override
    public boolean del(long chapterId) {
        Chapter chapter = chapterMapper.selectByChapterId(chapterId);
        int result = chapterMapper.delByChapterId(chapterId);
        if(result == 1){
            comicService.updateLatest(chapter.getCid());
            return true;
        }
        return false;
    }

    @Override
    public List<ChapterVO> getChapterVOList(Long cid) {
        List<Chapter> chapters = chapterMapper.selectByCid(cid);
        List<ChapterVO> chapterVOS = new ArrayList<ChapterVO>();
        for(Chapter chapter : chapters){
            ChapterVO chapterVO = new ChapterVO();
            chapterVO.setChapterId(chapter.getChapterId());
            chapterVO.setSection("第"+chapter.getSection()+"话");
            chapterVO.setTitle(chapter.getTitle());
            chapterVOS.add(chapterVO);
        }
        return chapterVOS;
    }

    @Override
    public List<ChapterVerifyVO> getChapterVerifyVOList() {
        List<Chapter> chapters = chapterMapper.selectForCheck();
        List<ChapterVerifyVO> chapterVerifyVOS = new ArrayList<ChapterVerifyVO>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Chapter chapter : chapters){
            ChapterVerifyVO chapterVerifyVO = new ChapterVerifyVO();
            chapterVerifyVO.setChapterId(chapter.getChapterId());
            chapterVerifyVO.setPosttime(formatter.format(chapter.getPosttime()));
            chapterVerifyVO.setChapterTitle(chapter.getTitle());
            chapterVerifyVO.setSection("第"+chapter.getSection()+"话");
            chapterVerifyVO.setVerify(chapter.getVerify());
            Comic comic = comicMapper.getByCid(chapter.getCid());
            chapterVerifyVO.setComicTitle(comic.getTitle());
            chapterVerifyVOS.add(chapterVerifyVO);
        }
        return chapterVerifyVOS;
    }

    @Override
    public boolean setVerify(Long chapterId, Integer verify) {
        int result = chapterMapper.checkByChapterId(chapterId, verify);
        if(result == 1){
            return true;
        }
        return false;
    }
}

