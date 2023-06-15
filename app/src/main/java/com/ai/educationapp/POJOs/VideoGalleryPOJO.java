package com.ai.educationapp.POJOs;

public class VideoGalleryPOJO{

    String id,video_title,video_src,video_category,sort_order;

    public VideoGalleryPOJO(String id, String video_title, String video_src, String video_category, String sort_order) {
        this.id = id;
        this.video_title = video_title;
        this.video_src = video_src;
        this.video_category = video_category;
        this.sort_order = sort_order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_src() {
        return video_src;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }

    public String getVideo_category() {
        return video_category;
    }

    public void setVideo_category(String video_category) {
        this.video_category = video_category;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
