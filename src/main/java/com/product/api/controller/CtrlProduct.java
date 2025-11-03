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
import com.product.api.dto.DtoProductIn;
import com.product.api.dto.DtoProductListOut;
import com.product.api.dto.DtoProductOut;
import com.product.api.service.SvcProduct;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Product", description = "Operaciones relacionadas con los productos")
@RestController
@RequestMapping("/product")
public class CtrlProduct {

	@Autowired
	SvcProduct svc;

	@Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
	@GetMapping
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		return svc.getProducts();
	}

	@Operation(summary = "Obtener un producto por ID", description = "Devuelve los detalles de un producto específico.")
	@GetMapping("/{id}")
	public ResponseEntity<DtoProductOut> getProduct(@PathVariable Integer id) {
		return svc.getProduct(id);
	}

	@Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto con los datos proporcionados.")
	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody DtoProductIn in) {
		return svc.createProduct(in);
	}

	@Operation(summary = "Actualizar un producto", description = "Actualiza los datos de un producto existente.")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody DtoProductIn in) {
		return svc.updateProduct(id, in);
	}

	@Operation(summary = "Habilitar un producto", description = "Habilita un producto cambiando su estado a activo.")
	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableProduct(@PathVariable Integer id) {
		return svc.enableProduct(id);
	}

	@Operation(summary = "Deshabilitar un producto", description = "Deshabilita un producto cambiando su estado a inactivo.")
	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableProduct(@PathVariable Integer id) {
		return svc.disableProduct(id);
	}

}
