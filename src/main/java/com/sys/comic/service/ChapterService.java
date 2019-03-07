package com.sys.comic.service;

import com.sys.comic.entity.Chapter;
import com.sys.comic.entity.vo.ChapterVO;

import java.util.List;

public interface ChapterService {

    boolean add(Long cid, Integer section, String title);

    List<Chapter> getChapterList(Long cid);

    boolean del(long chapterId);

    List<ChapterVO> getChapterVOList(Long cid);
}
