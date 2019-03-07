package com.sys.comic.service.impl;

import com.sys.comic.dao.ChapterMapper;
import com.sys.comic.entity.Chapter;
import com.sys.comic.entity.vo.ChapterVO;
import com.sys.comic.service.ChapterService;
import com.sys.comic.service.ComicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private ComicService comicService;

    @Override
    public boolean add(Long cid, Integer section, String title) {
        Chapter chapter = new Chapter();
        chapter.setCid(cid);
        chapter.setSection(section);
        chapter.setTitle(title);
        int result = chapterMapper.insert(chapter);
        if(result == 1){
            comicService.updateLatest(cid);
            return true;
        }
        return false;
    }

    @Override
    public List<Chapter> getChapterList(Long cid) {
        return chapterMapper.selectByCid(cid);
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
}

