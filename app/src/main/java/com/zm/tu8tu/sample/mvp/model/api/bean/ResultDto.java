package com.zm.tu8tu.sample.mvp.model.api.bean;

import java.io.Serializable;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/5/4
 * @description : 描述...
 */
public class ResultDto<T> implements Serializable {
    private T content;
    private String title;
    private int status;
    private int totalpage;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
}
