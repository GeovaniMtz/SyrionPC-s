package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoProductImageIn;
import com.product.api.dto.DtoProductImageOut;
import com.product.api.service.SvcProductImage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Product Image", description = "Operaciones relacionadas con las imágenes de productos")
@RestController
@RequestMapping("/product/{id}/image")
public class CtrlProductImage {

    @Autowired
    SvcProductImage svcProductImage;

    @Operation(summary = "Agregar una imagen a un producto", description = "Agrega una nueva imagen a un producto específico.")
    @PostMapping
    public ResponseEntity<ApiResponse> createProductImage(
            @PathVariable("id") Integer productId,
            @Valid @RequestBody DtoProductImageIn in) {
        return new ResponseEntity<>(svcProductImage.createProductImage(productId, in), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener imágenes de un producto", description = "Devuelve una lista de imágenes asociadas a un producto específico.")
    @GetMapping
    public ResponseEntity<List<DtoProductImageOut>> getProductImages(@PathVariable("id") Integer productId) {
        return new ResponseEntity<>(svcProductImage.getProductImages(productId), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una imagen de un producto", description = "Elimina una imagen específica de un producto.")
    @DeleteMapping("/{productImageId}")
    public ResponseEntity<ApiResponse> deleteProductImage(
            @PathVariable("id") Integer productId, // opcional en servicio, queda en la ruta
            @PathVariable Integer productImageId) {
        return new ResponseEntity<>(svcProductImage.deleteProductImage(productImageId), HttpStatus.OK);
    }
}
