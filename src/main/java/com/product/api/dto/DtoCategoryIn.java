package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para recibir datos de entrada al crear o actualizar una categoría.
 * Contiene validaciones para asegurar que los datos sean correctos.
 */
public class DtoCategoryIn {

    @NotNull(message = "El nombre de la categoría es requerido")
    @Size(min = 3, max = 100, message = "El nombre de la categoría debe tener entre 3 y 100 caracteres")
    @JsonProperty("category")
    private String category;

    @NotNull(message = "El tag es requerido")
    @Size(min = 2, max = 50, message = "El tag debe tener entre 2 y 50 caracteres")
    @JsonProperty("tag")
    private String tag;

    // ===== Getters =====

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    // ===== Setters =====

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}