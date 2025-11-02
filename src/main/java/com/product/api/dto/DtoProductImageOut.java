package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoProductImageOut {

    @JsonProperty("product_image_id")
    private Integer productImageId;

    @JsonProperty("image")
    private String image; // Aquí pondremos el Base64

    public DtoProductImageOut(Integer productImageId, String image) {
        this.productImageId = productImageId;
        this.image = image;
    }

    // Getters y Setters
    public Integer getProductImageId() {
        return productImageId;
    }
    public void setProductImageId(Integer productImageId) {
        this.productImageId = productImageId;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}