package com.sys.comic.dao;

import com.sys.comic.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {

    List<Chapter> selectByCid(long cid);

    List<Chapter> selectInManagementPage(long cid);

    int insert(Chapter chapter);

    int delByChapterId(long chapterId);

    Chapter selectLatest(long cid);

    Chapter selectByChapterId(long chapterId);

    List<Chapter> selectForCheck();

    int checkByChapterId(@Param("chapterId") long chapterId, @Param("verify") int verify);
}
