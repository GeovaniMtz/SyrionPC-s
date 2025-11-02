package com.product.api.commons.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.product.api.dto.DtoProductIn;
import com.product.api.dto.DtoProductListOut;
import com.product.api.dto.DtoProductOut;
import com.product.api.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapperProduct {

    // Mapeo para getProducts (lista)
    List<DtoProductListOut> fromProductList(List<Product> products);
    
    // Mapeo para getProduct (detalle)
    // Ignoramos 'category' porque lo pondremos manualmente en el servicio
    @Mapping(target = "category", ignore = true) 
    DtoProductOut fromProduct(Product product);
    
    // Mapeo para createProduct
    @Mappings({
        @Mapping(target = "product_id", ignore = true),
        @Mapping(target = "status", constant = "1") // Fija status a 1
    })
    Product fromDto(DtoProductIn in);
    
    // Mapeo para updateProduct
    // Mantiene el status que ya tenía el producto en la BD
    @Mappings({
        @Mapping(target = "status", source = "product.status"),
        @Mapping(target = "product_id", source = "id"),
        
        @Mapping(target = "gtin", source = "in.gtin"),
        @Mapping(target = "product", source = "in.product"),
        @Mapping(target = "description", source = "in.description"),
        @Mapping(target = "price", source = "in.price"),
        @Mapping(target = "stock", source = "in.stock"),
        @Mapping(target = "category_id", source = "in.category_id")
    })
    Product fromDto(Integer id, DtoProductIn in, Product product);
}