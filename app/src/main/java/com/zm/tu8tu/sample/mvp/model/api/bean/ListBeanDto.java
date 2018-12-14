package com.zm.tu8tu.sample.mvp.model.api.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/5/4
 * @description : 描述...
 */
public class ListBeanDto implements Serializable {
    private int total_pages;
    private int current_page;
    private List<DataBean> data;

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean implements Serializable {
        private int vrid;
        private String title;
        private String linenum;
        private String commentnum;
        private int productnum;
        private String isnew;
        private String imgmd5;
        private String style_name;

        public int getVrid() {
            return vrid;
        }

        public void setVrid(int vrid) {
            this.vrid = vrid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLinenum() {
            return linenum;
        }

        public void setLinenum(String linenum) {
            this.linenum = linenum;
        }

        public String getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(String commentnum) {
            this.commentnum = commentnum;
        }

        public int getProductnum() {
            return productnum;
        }

        public void setProductnum(int productnum) {
            this.productnum = productnum;
        }

        public String getIsnew() {
            return isnew;
        }

        public void setIsnew(String isnew) {
            this.isnew = isnew;
        }

        public String getImgmd5() {
            return imgmd5;
        }

        public void setImgmd5(String imgmd5) {
            this.imgmd5 = imgmd5;
        }

        public String getStyle_name() {
            return style_name;
        }

        public void setStyle_name(String style_name) {
            this.style_name = style_name;
        }
    }
}
