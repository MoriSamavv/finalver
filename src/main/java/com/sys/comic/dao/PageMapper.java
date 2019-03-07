package com.sys.comic.dao;

import com.sys.comic.entity.Page;

import java.util.List;

public interface PageMapper {

    List<Page> selectByChapterId(long chapterId);

    int insert(Page page);
}
