package com.product.api.service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoProductImageIn;
import com.product.api.dto.DtoProductImageOut;
import com.product.api.entity.ProductImage;
import com.product.api.exception.ApiException;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    RepoProductImage repo;

    @Autowired
    RepoProduct repoProduct; // Para validar que el producto exista

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public ApiResponse createProductImage(Integer productId, DtoProductImageIn in) {
        try {
            // 1. Validar que el producto exista
            if (!repoProduct.existsById(productId)) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
            }

            // 2. Lógica para guardar la imagen (Basado en C11)
            String base64Image = in.getImage();

            // 3. Quitar prefijo "data:image/png;base64," (Basado en C11)
            if (base64Image.startsWith("data:image")) {
                int commaIndex = base64Image.indexOf(",");
                if (commaIndex != -1) {
                    base64Image = base64Image.substring(commaIndex + 1);
                }
            }

            // 4. Decodificar Base64
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            String fileName = UUID.randomUUID().toString() + ".png"; // Nombre único

            // 5. Definir ruta
            Path imagePath = Paths.get(uploadDir, "img", "product", fileName);

            // 6. Guardar archivo
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);

            // 7. Guardar en BD (la ruta)
            ProductImage productImage = new ProductImage();
            productImage.setProductId(productId);
            productImage.setImage("/img/product/" + fileName); // Guardar ruta relativa
            productImage.setStatus(1);
            repo.save(productImage);

            return new ApiResponse("La imagen ha sido registrada");

        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al guardar la imagen: " + e.getMessage());
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo de imagen: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Error común si el Base64 es inválido
            throw new ApiException(HttpStatus.BAD_REQUEST, "El formato de la imagen (Base64) es inválido: " + e.getMessage());
        }
    }

    @Override
    public List<DtoProductImageOut> getProductImages(Integer productId) {
        try {
            // 1. Validar que el producto exista
            if (!repoProduct.existsById(productId)) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
            }

            // 2. Buscar todas las imágenes en la BD
            List<ProductImage> images = repo.findByProductId(productId);

            // 3. Convertir cada imagen a Base64 (Lógica de C12)
            return images.stream()
                    .map(image -> new DtoProductImageOut(
                            image.getProductImageId(),
                            readProductImageFile(image.getImage()) // Método helper
                    ))
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al consultar las imágenes: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteProductImage(Integer productImageId) {
        try {
            // 1. Validar que la imagen exista
            Optional<ProductImage> optionalImage = repo.findById(productImageId);
            if (optionalImage.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe");
            }
            ProductImage image = optionalImage.get();

            // 2. Eliminar archivo físico
            try {
                String imageUrl = image.getImage();
                if (imageUrl.startsWith("/")) {
                    imageUrl = imageUrl.substring(1);
                }
                Path imagePath = Paths.get(uploadDir, imageUrl);
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                // No lanzar excepción aquí, priorizar borrado de BD
                // Opcional: loggear este error
            }

            // 3. Eliminar registro de la BD
            repo.delete(image);

            return new ApiResponse("La imagen ha sido eliminada");

        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos al eliminar la imagen: " + e.getMessage());
        }
    }

    // Método Helper (Adaptado de C12)
    private String readProductImageFile(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return "";
            }
            if (imageUrl.startsWith("/")) {
                imageUrl = imageUrl.substring(1); // Quitar / inicial
            }
            Path imagePath = Paths.get(uploadDir, imageUrl);

            if (!Files.exists(imagePath)) {
                return ""; // Si el archivo no existe, devuelve string vacío
            }
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            // No lanzar excepción, solo devolver vacío para no romper la lista
            return ""; 
        }
    }
}