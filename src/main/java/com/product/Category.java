package com.product;

/**
 * Clase que representa una categoría dentro del sistema.
 * Cada categoría tiene un identificador único, un nombre,
 * un tag asociado y un status (1=activa, 0=eliminada).
 */
public class Category {
    /** Identificador único de la categoría (>=1). */
    private Integer category_id;

    /** Nombre de la categoría. */
    private String category;

    /** Etiqueta o tag que identifica la categoría. */
    private String tag;

    /** Estado de la categoría: 1 = activa, 0 = eliminada. */
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
