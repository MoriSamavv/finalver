package com.sys.comic.service.impl;

import com.sys.comic.dao.PageMapper;
import com.sys.comic.entity.Page;
import com.sys.comic.service.PageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private PageMapper pageMapper;

    @Override
    public boolean add(Long chapterId, String pageurl) {
        Page page = new Page();
        page.setChapterId(chapterId);
        page.setPageurl(pageurl);
        return pageMapper.insert(page) > 0 ? true : false;
    }

    @Override
    public List<Page> getPageList(Long chapterId) {
        return pageMapper.selectByChapterId(chapterId);
    }
}
