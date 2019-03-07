package com.sys.comic.dao;

import com.sys.comic.entity.Chapter;

import java.util.List;

public interface ChapterMapper {

    List<Chapter> selectByCid(long cid);

    int insert(Chapter chapter);

    int delByChapterId(long chapterId);

    Chapter selectLatest(long cid);

    Chapter selectByChapterId(long chapterId);

}
