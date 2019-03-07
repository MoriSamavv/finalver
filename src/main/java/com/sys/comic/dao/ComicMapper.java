package com.sys.comic.dao;

import com.sys.comic.entity.Comic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComicMapper {

    List<Comic> selectAll();

    int insert(Comic comic);

    int delByCid(long cid);

    int updateLatest(@Param("cid") long cid, @Param("latest") int latest);

    int updateOutsideurl(@Param("cid") long cid, @Param("outsideurl") String outsideurl);

    int updateInsideurl(@Param("cid") long cid, @Param("insideurl") String insideurl);

    Comic getByCid(long cid);
}
