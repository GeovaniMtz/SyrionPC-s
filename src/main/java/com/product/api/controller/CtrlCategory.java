package com.product.api.controller;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones CRUD de Category.
 * Define los endpoints disponibles para el módulo de categorías.
 */
@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    SvcCategory svc;

    /**
     * Obtiene todas las categorías.
     * GET /category
     *
     * @return Lista de todas las categorías
     */
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }

    /**
     * Obtiene solo las categorías activas (status = 1).
     * GET /category/active
     *
     * @return Lista de categorías activas
     */
    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive() {
        return ResponseEntity.ok(svc.findActive());
    }

    /**
     * Crea una nueva categoría.
     * POST /category
     *
     * @param in DTO con los datos de la categoría (category y tag)
     * @return ApiResponse con mensaje de confirmación
     */
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoCategoryIn in) {
        return ResponseEntity.ok(svc.create(in));
    }

    /**
     * Actualiza una categoría existente.
     * PUT /category/{id}
     *
     * @param id ID de la categoría a actualizar
     * @param in DTO con los nuevos datos de la categoría
     * @return ApiResponse con mensaje de confirmación
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Integer id,
            @Valid @RequestBody DtoCategoryIn in) {
        return ResponseEntity.ok(svc.update(in, id));
    }

    /**
     * Activa una categoría (cambia status a 1).
     * PATCH /category/{id}/enable
     *
     * @param id ID de la categoría a activar
     * @return ApiResponse con mensaje de confirmación
     */
    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.enable(id));
    }

    /**
     * Desactiva una categoría (cambia status a 0).
     * PATCH /category/{id}/disable
     *
     * @param id ID de la categoría a desactivar
     * @return ApiResponse con mensaje de confirmación
     */
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.disable(id));
    }
}