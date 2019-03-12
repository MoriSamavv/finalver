package com.sys.comic.service.impl;

import com.sys.comic.dao.ChapterMapper;
import com.sys.comic.dao.ComicMapper;
import com.sys.comic.dao.StarMapper;
import com.sys.comic.entity.Chapter;
import com.sys.comic.entity.Comic;
import com.sys.comic.entity.Star;
import com.sys.comic.entity.vo.ComicInnerCoverVO;
import com.sys.comic.entity.vo.ComicManagementVO;
import com.sys.comic.entity.vo.ComicOuterCoverVO;
import com.sys.comic.service.ComicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComicServiceImpl implements ComicService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ComicMapper comicMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private StarMapper starMapper;

    @Override
    public boolean add(String title, String author, String description) {
        Comic comic = new Comic();
        comic.setTitle(title);
        comic.setAuthor(author);
        comic.setDescription(description);
        comic.setPosttime(new Date());
        int result = comicMapper.insert(comic);
        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public List<ComicManagementVO> getComicManagementVOList() {
        List<Comic> comics = comicMapper.selectAll();
        List<ComicManagementVO> comicManagementVOS = new ArrayList<ComicManagementVO>();
        for(Comic comic : comics){
            ComicManagementVO comicManagementVO = new ComicManagementVO();
            comicManagementVO.setCid(comic.getCid());
            comicManagementVO.setTitle(comic.getTitle());
            comicManagementVO.setAuthor(comic.getAuthor());
            comicManagementVO.setDescription(comic.getDescription());
            comicManagementVO.setLatest("更新至"+comic.getLatest()+"话");
            comicManagementVOS.add(comicManagementVO);
        }
        return comicManagementVOS;
    }

    @Override
    public boolean del(long cid) {
        int result = comicMapper.delByCid(cid);
        List<Chapter> chapters = chapterMapper.selectByCid(cid);
        for(Chapter chapter : chapters){
            chapterMapper.delByChapterId(chapter.getChapterId());
        }
        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLatest(long cid) {
        Chapter chapter = chapterMapper.selectLatest(cid);
        if(chapter != null){
            logger.info("lll---"+chapter.getSection());
            int result = comicMapper.updateLatest(cid, chapter.getSection());
            logger.info("result:"+result);
            if(result == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCoverUrl(long cid, String coverUrl, String type) {
        if("first".equals(type)){
            int result = comicMapper.updateOutsideurl(cid, coverUrl);
            if(result == 1){
                return true;
            }
        }
        else if("second".equals(type)){
            int result = comicMapper.updateInsideurl(cid, coverUrl);
            if(result == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ComicOuterCoverVO> getComicOuterCoverVOList() {
        List<Comic> comics = comicMapper.selectAll();
        List<ComicOuterCoverVO> comicOuterCoverVOS = new ArrayList<ComicOuterCoverVO>();
        for(Comic comic : comics){
            ComicOuterCoverVO comicOuterCoverVO = new ComicOuterCoverVO();
            comicOuterCoverVO.setCid(comic.getCid());
            comicOuterCoverVO.setTitle(comic.getTitle());
            comicOuterCoverVO.setAuthor(comic.getAuthor());
            comicOuterCoverVO.setOutsideurl(comic.getOutsideurl());
            comicOuterCoverVOS.add(comicOuterCoverVO);
        }
        return comicOuterCoverVOS;
    }

    @Override
    public ComicInnerCoverVO getComicInnerCoverVO(long uid, long cid) {
        Comic comic = comicMapper.getByCid(cid);
        ComicInnerCoverVO comicInnerCoverVO = new ComicInnerCoverVO();
        comicInnerCoverVO.setCid(comic.getCid());
        comicInnerCoverVO.setTitle(comic.getTitle());
        comicInnerCoverVO.setAuthor(comic.getAuthor());
        comicInnerCoverVO.setDescription(comic.getDescription());
        comicInnerCoverVO.setInsideurl(comic.getInsideurl());
        Star star = starMapper.get(uid, cid);
        if(star != null){
            comicInnerCoverVO.setStar(star.getLike());
        }else{
            comicInnerCoverVO.setStar(false);
        }
        return comicInnerCoverVO;
    }
}
