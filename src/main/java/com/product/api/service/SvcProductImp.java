package com.product.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.DtoProductIn;
import com.product.api.dto.DtoProductListOut;
import com.product.api.dto.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.api.repository.RepoProduct;
import com.product.api.commons.dto.ApiResponse;
import com.product.api.commons.mapper.MapperProduct;
import com.product.api.exception.ApiException;
import com.product.api.exception.DBAccessException;

@Service
public class SvcProductImp implements SvcProduct{
	
	@Autowired
	RepoProduct repo;

	@Autowired
    RepoCategory repoCategory;
	
	@Autowired
	MapperProduct mapper;

	@Override
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		try {
			List<Product> products = repo.findAll();
			return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al consultar los productos: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<DtoProductOut> getProduct(Integer id) {
		try {
			// 1. Validar que el producto existe
			Product product = validateProductId(id);

			// 2. Buscar la categoría
			Category category = repoCategory.findById(product.getCategory_id()).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "El id de categoría " + product.getCategory_id() + " asociado al producto no existe")
            );

			// 3. Mapear a DtoProductOut
            DtoProductOut dto = mapper.fromProduct(product);
            dto.setCategory(category.getCategory()); // Asignar nombre de categoría
			
			return new ResponseEntity<>(dto, HttpStatus.OK);

		} catch (ApiException e) {
            throw e; // Re-lanzar ApiException
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al buscar el producto: " + e.getMessage());
        }
	}

	@Override
	public ResponseEntity<ApiResponse> createProduct(DtoProductIn in) {
		try {
			// Validar que la categoría exista antes de guardar
            if (!repoCategory.existsById(in.getCategory_id())) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
            }
			Product product = mapper.fromDto(in);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido registrado"), HttpStatus.CREATED);
		} catch (ApiException e) {
            throw e; // Re-lanzar ApiException
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_product_gtin"))
                throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
            if (e.getLocalizedMessage().contains("ux_product_product"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
            
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al crear el producto: " + e.getMessage());
        }
	}

	@Override
	public ResponseEntity<ApiResponse> updateProduct(Integer id, DtoProductIn in) {
		try {
			Product product = validateProductId(id);

			// Validar que la nueva categoría exista
            if (!repoCategory.existsById(in.getCategory_id())) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
            }

			// Usar el mapeo que mantiene el status original
			Product productUpdated = mapper.fromDto(id, in, product);
			repo.save(productUpdated);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido actualizado"), HttpStatus.OK);
		} catch (ApiException e) {
            throw e; // Re-lanzar ApiException
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_product_gtin"))
                throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
            if (e.getLocalizedMessage().contains("ux_product_product"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
            
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al actualizar el producto: " + e.getMessage());
        }
	}

	@Override
	public ResponseEntity<ApiResponse> enableProduct(Integer id) {
		try {
			Product product = validateProductId(id);
			if(product.getStatus() == 1){
                throw new ApiException(HttpStatus.BAD_REQUEST, "El producto ya está activo");
            }
			product.setStatus(1);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido activado"), HttpStatus.OK);
		} catch (ApiException e) {
            throw e; // Re-lanzar ApiException
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al activar el producto: " + e.getMessage());
        }
	}

	@Override
	public ResponseEntity<ApiResponse> disableProduct(Integer id) {
		try {
			Product product = validateProductId(id);
			if(product.getStatus() == 0){
                throw new ApiException(HttpStatus.BAD_REQUEST, "El producto ya está desactivado");
            }
			product.setStatus(0);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido desactivado"), HttpStatus.OK);
		} catch (ApiException e) {
            throw e; // Re-lanzar ApiException
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al desactivar el producto: " + e.getMessage());
        }
	}
	
    private Product validateProductId(Integer id) {
        Optional<Product> optionalProduct = repo.findById(id);
        
        // Si está vacío, lanza la excepción
        if (optionalProduct.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No se encontró el producto con ID: " + id);
        }
        
        // Si existe, lo devuelve
        return optionalProduct.get();
    }

}
