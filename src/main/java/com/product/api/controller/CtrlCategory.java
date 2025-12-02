package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar las operaciones CRUD de Category.
 * Define los endpoints disponibles para el módulo de categorías.
 */
@Tag(name = "Category", description = "Operaciones relacionadas con las categorías de productos")
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
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías disponibles.")
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
    @Operation(summary = "Obtener categorías activas", description = "Devuelve una lista de categorías que están activas (status = 1).")
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
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría con los datos proporcionados.")
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
    @Operation(summary = "Actualizar una categoría", description = "Actualiza los datos de una categoría existente.")
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
    @Operation(summary = "Activar una categoría", description = "Activa una categoría cambiando su estado a activo (status = 1).")
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
    @Operation(summary = "Desactivar una categoría", description = "Desactiva una categoría cambiando su estado a inactivo (status = 0).")
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.disable(id));
    }
}