package com.sys.comic.service.impl;

import com.sys.comic.dao.ChapterMapper;
import com.sys.comic.dao.ComicMapper;
import com.sys.comic.dao.StarMapper;
import com.sys.comic.entity.Chapter;
import com.sys.comic.entity.Comic;
import com.sys.comic.entity.Star;
import com.sys.comic.entity.vo.StarVO;
import com.sys.comic.service.StarService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StarServiceImpl implements StarService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private StarMapper starMapper;
    @Autowired
    private ComicMapper comicMapper;
    @Autowired
    private ChapterMapper chapterMapper;


    @Override
    public Star get(long uid, long cid) {
        return starMapper.get(uid, cid);
    }

    @Override
    public boolean updateLike(long uid, long cid, boolean like) {
        int result = starMapper.updateLike(uid, cid, like);
        if(result == 0){
            Star temStar = new Star();
            temStar.setUid(uid);
            temStar.setCid(cid);
            temStar.setLike(true);
            temStar.setChapterId(0);
            starMapper.insert(temStar);
        }
        return true;
    }

    @Override
    public boolean updateChapterId(long uid, long cid, long chapterId) {
        int result = starMapper.updateChapterId(uid, cid, chapterId);
        if(result == 0){
            Star temStar = new Star();
            temStar.setUid(uid);
            temStar.setCid(cid);
            temStar.setLike(false);
            temStar.setChapterId(chapterId);
            starMapper.insert(temStar);
        }
        return true;
    }

    @Override
    public List<StarVO> getStarVOList(long uid) {
        List<Star> stars = starMapper.selectByUid(uid);
        List<StarVO> starVOS = new ArrayList<StarVO>();
        for(Star star : stars){
            StarVO starVO = new StarVO();
            starVO.setCid(star.getCid());
            starVO.setChapterId(star.getChapterId());
            Comic comic = comicMapper.getByCid(star.getCid());
            starVO.setTitle(comic.getTitle());
            starVO.setLatest("更新至"+comic.getLatest()+"话");
            Chapter chapter = chapterMapper.selectByChapterId(star.getChapterId());
            if(chapter == null){
                starVO.setRead("阅读至0话");
            }else{
                starVO.setRead("阅读至"+chapter.getSection()+"话");
            }
            starVOS.add(starVO);
        }
        return starVOS;
    }
}
