package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.dto.DtoProductIn;
import com.product.api.dto.DtoProductListOut;
import com.product.api.dto.DtoProductOut;
import com.product.api.commons.dto.ApiResponse;

public interface SvcProduct {

	public ResponseEntity<List<DtoProductListOut>> getProducts();
	public ResponseEntity<DtoProductOut> getProduct(Integer id);
	public ResponseEntity<ApiResponse> createProduct(DtoProductIn in);
	public ResponseEntity<ApiResponse> updateProduct(Integer id, DtoProductIn in);
	public ResponseEntity<ApiResponse> enableProduct(Integer id);
	public ResponseEntity<ApiResponse> disableProduct(Integer id);
    public ResponseEntity<ApiResponse> updateProductStock(Integer id, Integer stock);

}
