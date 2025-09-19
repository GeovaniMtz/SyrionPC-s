package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.exception.ApiException; 
import com.product.api.repository.RepoCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {
    @Autowired
    RepoCategory repo;

    @Override
    public ResponseEntity<List<Category>> getCategory() {
        try {
            // En el caso exitoso, envolvemos la lista en un ResponseEntity con estado 200 OK
            return new ResponseEntity<>(repo.getCategory(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error en la base de datos al consultar las categorías: " + e.getMessage());
        }
    }
}