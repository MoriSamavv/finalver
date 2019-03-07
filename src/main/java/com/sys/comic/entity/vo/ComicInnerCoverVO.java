package com.sys.comic.entity.vo;

public class ComicInnerCoverVO {

    private long cid;

    private String title;

    private String author;

    private String description;

    private boolean star;

    private String insideurl;

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

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public String getInsideurl() {
        return insideurl;
    }

    public void setInsideurl(String insideurl) {
        this.insideurl = insideurl;
    }
}
