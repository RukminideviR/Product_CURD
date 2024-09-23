package com.mouritech.product.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mouritech.product.dto.ProductDto;

@Service
public interface ProductService {
	ProductDto createProduct(ProductDto dto);

	List<ProductDto> getAllProducts();

	ProductDto getProductById(Long id);

	ProductDto updateProduct(Long id, ProductDto dto);

	void deleteProduct(Long id);
	
	Object getProductPageination(Integer pageNumber, Integer pageSize);


}
