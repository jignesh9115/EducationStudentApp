package com.ai.educationapp.POJOs;

public class PhotoGalleryPOJO {

    String id,image_title,image_img,image_category,sort_order;

    public PhotoGalleryPOJO(String id, String image_title, String image_img, String image_category, String sort_order) {
        this.id = id;
        this.image_title = image_title;
        this.image_img = image_img;
        this.image_category = image_category;
        this.sort_order = sort_order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getImage_img() {
        return image_img;
    }

    public void setImage_img(String image_img) {
        this.image_img = image_img;
    }

    public String getImage_category() {
        return image_category;
    }

    public void setImage_category(String image_category) {
        this.image_category = image_category;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
