package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CtrlProduct {

    @Autowired
    SvcCategory svc;

    @GetMapping("/category")
    // El método ahora devuelve ResponseEntity directamente desde el servicio
    public ResponseEntity<List<Category>> getCategories() {
        return svc.getCategory();
    }
}
