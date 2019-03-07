package com.sys.comic.service;

import com.sys.comic.entity.vo.ComicInnerCoverVO;
import com.sys.comic.entity.vo.ComicManagementVO;
import com.sys.comic.entity.vo.ComicOuterCoverVO;

import java.util.List;

public interface ComicService {

    boolean add(String title, String author, String description);

    List<ComicManagementVO> getComicManagementVOList();

    boolean del(long cid);

    boolean updateLatest(long cid);

    boolean updateCoverUrl(long cid, String coverUrl, String type);

    List<ComicOuterCoverVO> getComicOuterCoverVOList();

    ComicInnerCoverVO getComicInnerCoverVO(long uid, long cid);
}
