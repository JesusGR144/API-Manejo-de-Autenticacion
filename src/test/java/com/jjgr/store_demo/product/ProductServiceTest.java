package com.jjgr.store_demo.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private ProductService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ProductService(productRepository);
    }

    @Test
    void getProducts(){
        //Dado
        Product product1 = new Product("Libreta",10, 19.99);
        Product product2 = new Product("Lapiz", 50, 5);

        List<Product> products = List.of(product1,product2);
        when(productRepository.findAll()).thenReturn(products);

        //Cuando
        List<Product> result = underTest.getProducts();

        //Entonces
        verify(productRepository, Mockito.times(2)).findAll();
        assertEquals(products, result);
    }

    @Test
    void addProduct() {
        //Dado
        Product product = new Product("Colores", 20, 70);

        //Cuando
        underTest.addProduct(product);

        //Entonces
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertEquals(product, capturedProduct);
    }

    @Test
    void deleteProduct(){
        //Dado
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        //Cuando
        underTest.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test 
    void deleteProductWhenUserDoesNotExist(){
        //Dado
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        //Cuando
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->{
            underTest.deleteProduct(productId);
        });

        //Entonces
        assertEquals("Product with id "+productId+" does not exist", exception.getMessage());
        verify(productRepository, never()).deleteById(productId);
    }
}
