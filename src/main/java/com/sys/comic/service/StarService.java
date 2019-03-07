package com.sys.comic.service;

import com.sys.comic.entity.Star;
import com.sys.comic.entity.vo.StarVO;

import java.util.List;

public interface StarService {

    Star get(long uid, long cid);

    boolean updateLike(long uid, long cid, boolean like);

    boolean updateChapterId(long uid, long cid, long chapterId);

    List<StarVO> getStarVOList(long uid);
}
