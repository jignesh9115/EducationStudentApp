package com.ai.educationapp.POJOs;

public class GalleryCategoryPOJO {

    String id,category_title,category_image,sort_order;

    public GalleryCategoryPOJO(String id, String category_title, String category_image, String sort_order) {
        this.id = id;
        this.category_title = category_title;
        this.category_image = category_image;
        this.sort_order = sort_order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
