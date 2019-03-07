package com.sys.comic.entity;

import java.util.Date;

public class Comic {

    private long cid;

    private String title;

    private String author;

    private String description;

    private int latest;

    private Date posttime;

    private String outsideurl;

    private String insideurl;

    private boolean del;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLatest() {
        return latest;
    }

    public void setLatest(int latest) {
        this.latest = latest;
    }

    public Date getPosttime() {
        return posttime;
    }

    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    public String getOutsideurl() {
        return outsideurl;
    }

    public void setOutsideurl(String outsideurl) {
        this.outsideurl = outsideurl;
    }

    public String getInsideurl() {
        return insideurl;
    }

    public void setInsideurl(String insideurl) {
        this.insideurl = insideurl;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }
}
