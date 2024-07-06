package com.jjgr.store_demo.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jjgr.store_demo.exceptions.ApiRequestException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //GET all
    public List<Product> getProducts(){
        if (productRepository.findAll().isEmpty()) {
            throw new ApiRequestException("No products found");
        }
        return productRepository.findAll();
    }

    //POST
    public void addProduct(Product product){
        Optional<Product> productIsPresent = productRepository.findByName(product.getName());

        if(productIsPresent.isPresent()){
            throw new IllegalStateException("Product is alredy taken");
        }

        productRepository.save(product);
    }

    //DELETE
    public void deleteProduct(Long id){
        boolean productExists = productRepository.existsById(id);
        if(!productExists){
            throw new IllegalStateException("Product with id "+id+" does not exist");
        }
        productRepository.deleteById(id);
    }
}
