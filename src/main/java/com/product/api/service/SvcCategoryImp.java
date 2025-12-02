package com.product.api.service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.exception.ApiException;
import com.product.api.repository.RepoCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Category.
 * Contiene la lógica de negocio para las operaciones CRUD.
 */
@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;

    @Override
    public List<Category> findAll() {
        try {
            return repo.findAllOrdered();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en la base de datos al consultar las categorías: " + e.getMessage());
        }
    }

    @Override
    public List<Category> findActive() {
        try {
            return repo.findActive();
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en la base de datos al consultar las categorías activas: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse create(DtoCategoryIn in) {
        try {
            // Validar que el nombre de la categoría no exista
            Optional<Category> existingByCategory = repo.findByCategory(in.getCategory());
            if (existingByCategory.isPresent()) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Ya existe una categoría con el nombre: " + in.getCategory());
            }

            // Validar que el tag no exista
            Optional<Category> existingByTag = repo.findByTag(in.getTag());
            if (existingByTag.isPresent()) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Ya existe una categoría con el tag: " + in.getTag());
            }

            // Crear la nueva categoría
            Category category = new Category();
            category.setCategory(in.getCategory());
            category.setTag(in.getTag());
            category.setStatus(1); // Por defecto, la categoría está activa

            repo.save(category);

            return new ApiResponse("La categoría ha sido registrada");

        } catch (ApiException e) {
            throw e; // Re-lanzar ApiException para que sea manejada por el handler
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en la base de datos al crear la categoría: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse update(DtoCategoryIn in, Integer id) {
        try {
            // Verificar que la categoría exista
            Optional<Category> optionalCategory = repo.findById(id);
            if (optionalCategory.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND,
                        "No se encontró la categoría con ID: " + id);
            }

            // Validar que el nombre no esté duplicado (excluyendo la categoría actual)
            Optional<Category> existingByCategory = repo.findByCategoryAndNotId(in.getCategory(), id);
            if (existingByCategory.isPresent()) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Ya existe otra categoría con el nombre: " + in.getCategory());
            }

            // Validar que el tag no esté duplicado (excluyendo la categoría actual)
            Optional<Category> existingByTag = repo.findByTagAndNotId(in.getTag(), id);
            if (existingByTag.isPresent()) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Ya existe otra categoría con el tag: " + in.getTag());
            }

            // Actualizar la categoría
            Category category = optionalCategory.get();
            category.setCategory(in.getCategory());
            category.setTag(in.getTag());

            repo.save(category);

            return new ApiResponse("La categoría ha sido actualizada");

        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en la base de datos al actualizar la categoría: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse enable(Integer id) {
        try {
            // Verificar que la categoría exista
            Optional<Category> optionalCategory = repo.findById(id);
            if (optionalCategory.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND,
                        "No se encontró la categoría con ID: " + id);
            }

            // Activar la categoría
            Category category = optionalCategory.get();

            // Verificar si ya está activa
            if (category.getStatus() == 1) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "La categoría ya está activa");
            }

            category.setStatus(1);
            repo.save(category);

            return new ApiResponse("La categoría ha sido activada");

        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en la base de datos al activar la categoría: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse disable(Integer id) {
        try {
            // Verificar que la categoría exista
            Optional<Category> optionalCategory = repo.findById(id);
            if (optionalCategory.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND,
                        "No se encontró la categoría con ID: " + id);
            }

            // Desactivar la categoría
            Category category = optionalCategory.get();

            // Verificar si ya está desactivada
            if (category.getStatus() == 0) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "La categoría ya está desactivada");
            }

            category.setStatus(0);
            repo.save(category);

            return new ApiResponse("La categoría ha sido desactivada");

        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en la base de datos al desactivar la categoría: " + e.getMessage());
        }
    }
}