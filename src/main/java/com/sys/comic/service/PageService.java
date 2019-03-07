package com.sys.comic.service;

import com.sys.comic.entity.Page;

import java.util.List;

public interface PageService {

    boolean add(Long chapterId, String pageurl);

    List<Page> getPageList(Long chapterId);
}
