package com.ai.educationapp.POJOs;

public class FeeDetailPOJO {

    String id,title,fee_type_id,base_amount;

    public FeeDetailPOJO(String id, String title, String fee_type_id, String base_amount) {
        this.id = id;
        this.title = title;
        this.fee_type_id = fee_type_id;
        this.base_amount = base_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFee_type_id() {
        return fee_type_id;
    }

    public void setFee_type_id(String fee_type_id) {
        this.fee_type_id = fee_type_id;
    }

    public String getBase_amount() {
        return base_amount;
    }

    public void setBase_amount(String base_amount) {
        this.base_amount = base_amount;
    }
}
