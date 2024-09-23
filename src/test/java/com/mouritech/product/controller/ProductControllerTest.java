package com.mouritech.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mouritech.product.dto.ProductDto;
import com.mouritech.product.service.ProductService;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void createProductTest() {
        ProductDto product = new ProductDto();
        product.setProductId(1L);  
        product.setProductName("Pen");  

        when(productService.createProduct(product)).thenReturn(product);
        ResponseEntity<ProductDto> entity = productController.createProduct(product);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertEquals(product, entity.getBody());
    }

    @Test
    void updateProductTest() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductDescription("Updated Product");

        when(productService.updateProduct(1L, productDto)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.updateProduct(1L, productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
    }

    

    @Test
    void getAllProductsTest() {
        List<ProductDto> productList = new ArrayList<>();
        ProductDto product1 = new ProductDto();
        product1.setProductId(1L);
        product1.setProductDescription("Product 1");
        
        ProductDto product2 = new ProductDto();
        product2.setProductId(2L);
        product2.setProductDescription("Product 2");
        
        productList.add(product1);
        productList.add(product2);
        
        when(productService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<ProductDto>> entity = productController.getAllProducts();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(productList, entity.getBody());
    }

    @Test
    void getProductByIdTest() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductDescription("Sample Product");

        when(productService.getProductById(1L)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
    }
    @Test
    void deleteProductByIdTest() {
        Long prodId = 10L;

        // Mocking the service delete method (assuming it returns void)
        doNothing().when(productService).deleteProduct(prodId);

        // Call the controller method and capture the response
        ResponseEntity<Void> responseEntity = productController.deleteProduct(prodId);

        // Assert the response is 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verify that the service's delete method was called exactly once
        verify(productService, times(1)).deleteProduct(prodId);
    }
    @Test
    void pageTest() {
        List<ProductDto> productPage = new ArrayList<>();
        when(productService.getProductPageination(1, 10)).thenReturn(productPage);

        ResponseEntity<?> entity = productController.page(1, 10);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(productPage, entity.getBody());
    }
}

