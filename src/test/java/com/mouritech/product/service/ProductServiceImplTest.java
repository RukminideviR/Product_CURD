package com.mouritech.product.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.mouritech.product.dao.ProductRepository;
import com.mouritech.product.dto.ProductDto;
import com.mouritech.product.entity.Product;
import com.mouritech.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProductTest() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductName("Laptop");

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");

        when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto createdProduct = productService.createProduct(productDto);

        assertNotNull(createdProduct);
        assertEquals(productDto.getProductId(), createdProduct.getProductId());
        assertEquals(productDto.getProductName(), createdProduct.getProductName());
    }

    @Test
    void getAllProductsTest() {
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setProductName("Laptop");

        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("Phone");

        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);
        when(modelMapper.map(product1, ProductDto.class)).thenReturn(new ProductDto(1L, "Laptop", 90000.89, "this is laptop"));
        when(modelMapper.map(product2, ProductDto.class)).thenReturn(new ProductDto(2L, "Phone", 200000.90, "This is mobile"));

        List<ProductDto> retrievedProducts = productService.getAllProducts();

        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductByIdTest() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");

        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductName("Laptop");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto retrievedProduct = productService.getProductById(1L);

        assertNotNull(retrievedProduct);
        assertEquals(productDto.getProductId(), retrievedProduct.getProductId());
        assertEquals(productDto.getProductName(), retrievedProduct.getProductName());
    }

    @Test
    void updateProductTest() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");

        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductName("Updated Laptop");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto updatedProduct = productService.updateProduct(1L, productDto);

        assertNotNull(updatedProduct);
        assertEquals(productDto.getProductId(), updatedProduct.getProductId());
        assertEquals(productDto.getProductName(), updatedProduct.getProductName());
    }

    @Test
    void deleteProductTest() {
        Long productId = 1L;

        Product product = new Product();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void getProductPaginationTest() {
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setProductName("Laptop");

        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("Phone");

        List<Product> products = Arrays.asList(product1, product2);
        PageRequest pageable = PageRequest.of(0, 2);
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Object paginationResult = productService.getProductPageination(0, 2);

        assertNotNull(paginationResult);
        verify(productRepository, times(1)).findAll(pageable);
    }
}

