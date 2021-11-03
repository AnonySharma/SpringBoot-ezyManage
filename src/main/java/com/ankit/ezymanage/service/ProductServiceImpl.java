package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.dao.ProductDAO;
import com.ankit.ezymanage.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public boolean addProduct(Product product) {
        productDAO.addProduct(product);
        return true;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public Product getProduct(int id) {
        return productDAO.getProductById(id);
    }

    @Override
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }

}
