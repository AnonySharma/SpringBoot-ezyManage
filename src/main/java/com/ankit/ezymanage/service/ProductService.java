package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Product;

public interface ProductService {
    boolean addProduct(Product product);

    Product getProduct(int id);

    void deleteProduct(int id);

    List<Product> getAllProducts();

}
