package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.DtoProductIn;
import com.product.api.dto.DtoProductListOut;
import com.product.api.dto.DtoProductOut;
import com.product.api.service.SvcProduct;
import com.product.api.commons.dto.ApiResponse;
import com.product.api.exception.ApiException;

import com.product.api.dto.DtoProductImageIn;
import com.product.api.dto.DtoProductImageOut;
import com.product.api.service.SvcProductImage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class CtrlProduct {

	@Autowired
	SvcProduct svc;

	@Autowired
    SvcProductImage svcProductImage;

	@GetMapping
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		return svc.getProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DtoProductOut> getProduct(@PathVariable Integer id) {
		return svc.getProduct(id);
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody DtoProductIn in) {
		return svc.createProduct(in);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody DtoProductIn in) {
		return svc.updateProduct(id, in);
	}

	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableProduct(@PathVariable Integer id) {
		return svc.enableProduct(id);
	}

	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableProduct(@PathVariable Integer id) {
		return svc.disableProduct(id);
	}

	@PostMapping("/{id}/image")
    public ResponseEntity<ApiResponse> createProductImage(
            @PathVariable("id") Integer id, // Este es el product_id
            @Valid @RequestBody DtoProductImageIn in) {
        return new ResponseEntity<>(svcProductImage.createProductImage(id, in), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<List<DtoProductImageOut>> getProductImages(
            @PathVariable("id") Integer id) { // Este es el product_id
        return new ResponseEntity<>(svcProductImage.getProductImages(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/image/{productImageId}")
    public ResponseEntity<ApiResponse> deleteProductImage(
            @PathVariable("id") Integer id, // No se usa, pero es parte de la ruta
            @PathVariable("productImageId") Integer productImageId) {
        return new ResponseEntity<>(svcProductImage.deleteProductImage(productImageId), HttpStatus.OK);
    }
}
