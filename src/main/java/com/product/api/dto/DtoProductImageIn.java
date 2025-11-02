package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class DtoProductImageIn {

    @JsonProperty("image")
    @NotNull(message="image es obligatorio")
    private String image; // String en Base64

    // Getter y Setter
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}