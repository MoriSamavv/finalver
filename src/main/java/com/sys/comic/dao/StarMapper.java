package com.sys.comic.dao;

import com.sys.comic.entity.Star;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StarMapper {

    List<Star> selectByUid(long uid);

    Star get(@Param("uid") long uid, @Param("cid") long cid);

    int insert(Star star);

    int updateLike(@Param("uid") long uid, @Param("cid") long cid, @Param("like") boolean like);

    int updateChapterId(@Param("uid") long uid, @Param("cid") long cid, @Param("chapterId") long chapterId);
}
