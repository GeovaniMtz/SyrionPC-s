package com.product.api.service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la entidad Category.
 * Incluye operaciones CRUD completas.
 */
public interface SvcCategory {

    /**
     * Obtiene todas las categorías (sin filtrar por status).
     * @return Lista de todas las categorías
     */
    public List<Category> findAll();

    /**
     * Obtiene solo las categorías activas (status = 1).
     * @return Lista de categorías activas
     */
    public List<Category> findActive();

    /**
     * Crea una nueva categoría.
     * @param in DTO con los datos de la categoría a crear
     * @return ApiResponse con mensaje de confirmación
     */
    public ApiResponse create(DtoCategoryIn in);

    /**
     * Actualiza una categoría existente.
     * @param in DTO con los nuevos datos de la categoría
     * @param id Identificador de la categoría a actualizar
     * @return ApiResponse con mensaje de confirmación
     */
    public ApiResponse update(DtoCategoryIn in, Integer id);

    /**
     * Activa una categoría (cambia status a 1).
     * @param id Identificador de la categoría a activar
     * @return ApiResponse con mensaje de confirmación
     */
    public ApiResponse enable(Integer id);

    /**
     * Desactiva una categoría (cambia status a 0).
     * @param id Identificador de la categoría a desactivar
     * @return ApiResponse con mensaje de confirmación
     */
    public ApiResponse disable(Integer id);
}