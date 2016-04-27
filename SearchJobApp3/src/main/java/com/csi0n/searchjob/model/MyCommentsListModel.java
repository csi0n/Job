package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/4/27 0027.
 */
public class MyCommentsListModel extends BaseBean {
    private List<MyCommentsModel> data;

    public List<MyCommentsModel> getData() {
        return data;
    }

    public void setData(List<MyCommentsModel> data) {
        this.data = data;
    }

    public static class MyCommentsModel{

        /**
         * name : 明芳汽配
         * image : upload/default/default_company_image.png
         * intro : 这个企业很懒，什么介绍都没留下
         * content : 我是一条评论
         * add_time : 1461503508
         */
private String id;
        private String name;
        private String image;
        private String intro;
        private String content;
        private long add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }
    }
}
