package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * Clase que representa una categoría dentro del sistema.
 * Cada categoría tiene un identificador único, un nombre,
 * un tag asociado y un status (1=activa, 0=eliminada).
 */
@Entity
@Table(name = "category")
public class Category {

    /** Identificador único de la categoría (>=1). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("category_id")
    @Column(name = "category_id")
    private Integer category_id;

    /** Nombre de la categoría. */
    @JsonProperty("category")
    @Column(name = "category", nullable = false, length = 100)
    private String category;

    /** Etiqueta o tag que identifica la categoría. */
    @JsonProperty("tag")
    @Column(name = "tag", nullable = false, length = 50)
    private String tag;

    /** Estado de la categoría: 1 = activa, 0 = eliminada. */
    @JsonProperty("status")
    @Column(name = "status", nullable = false)
    private Integer status;

    // ===== Getters =====

    /** @return el id de la categoría */
    public Integer getCategory_id() { return category_id; }

    /** @return el nombre de la categoría */
    public String getCategory() { return category; }

    /** @return el tag de la categoría */
    public String getTag() { return tag; }

    /** @return el status de la categoría (1 o 0) */
    public Integer getStatus() { return status; }

    // ===== Setters =====

    /** @param category_id id de la categoría (>=1) */
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }

    /** @param category nombre de la categoría */
    public void setCategory(String category) { this.category = category; }

    /** @param tag etiqueta de la categoría */
    public void setTag(String tag) { this.tag = tag; }

    /** @param status estado de la categoría (1=activa, 0=eliminada) */
    public void setStatus(Integer status) { this.status = status; }
}