package com.mouritech.product.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mouritech.product.dao.ProductRepository;
import com.mouritech.product.dto.ProductDto;
import com.mouritech.product.entity.Product;
import com.mouritech.product.exception.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ProductRepository productRepository;

	@Override
	public ProductDto createProduct(ProductDto dto) {
		Product product = modelMapper.map(dto, Product.class);
		Product response = productRepository.save(product);
		return modelMapper.map(response, ProductDto.class);
	}
	
	@Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                       .map(product -> modelMapper.map(product, ProductDto.class))
                       .collect(Collectors.toList());
    }
	
        @Override
        public ProductDto getProductById(Long id) {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
            return modelMapper.map(product, ProductDto.class);
        }

        @Override
        public ProductDto updateProduct(Long id, ProductDto dto) {
            Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
            
            modelMapper.map(dto, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            return modelMapper.map(updatedProduct, ProductDto.class);
        }

        @Override
        public void deleteProduct(Long id) {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
            productRepository.delete(product);
        }

		@Override
		public Object getProductPageination(Integer pageNumber, Integer pageSize) {
			PageRequest pageable= PageRequest.of(pageNumber,pageSize);
			
			return productRepository.findAll(pageable);
			
		}

        

}
