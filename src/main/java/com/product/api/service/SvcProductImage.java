package com.product.api.service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoProductImageIn;
import com.product.api.dto.DtoProductImageOut;
import java.util.List;

public interface SvcProductImage {

    ApiResponse createProductImage(Integer productId, DtoProductImageIn in);

    List<DtoProductImageOut> getProductImages(Integer productId);

    ApiResponse deleteProductImage(Integer productImageId);
}