package com.ankit.ezymanage.dao;

import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.utils.RowMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products(id, name, description, image, price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getName(), product.getDescription(), product.getImage(),
                product.getPrice());
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, image = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getImage(), product.getPrice(),
                product.getId());
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, RowMappers.productRowMapper, id);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, RowMappers.productRowMapper);
    }

}
