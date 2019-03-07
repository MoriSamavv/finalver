package com.sys.comic.view;

public class JsonView {

    private String status;

    private Object data;

    private String message;

    public JsonView(Object data) {
        this.status = "200";
        this.data = data;
    }

    public JsonView(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
