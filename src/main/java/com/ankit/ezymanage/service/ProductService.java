package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.Product;

public interface ProductService {
    public boolean addProduct(Product product);

    public Product getProduct(int id);

    public void deleteProduct(int id);

    public List<Product> getAllProducts();

}
