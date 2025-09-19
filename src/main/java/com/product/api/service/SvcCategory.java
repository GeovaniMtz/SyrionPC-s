package com.product.api.service;

import com.product.api.entity.Category;
import org.springframework.http.ResponseEntity; 
import java.util.List;

public interface SvcCategory {

    // El método ahora devuelve un ResponseEntity
    public ResponseEntity<List<Category>> getCategory();
}