package com.ankit.ezymanage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ankit.ezymanage.model.Shop;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDAO {
    private final JdbcTemplate jdbcTemplate;

    public ShopDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createShop(Shop shop) {
        final String sql = "INSERT INTO shops(id, name, type, phone, email, gstin, owner) VALUES(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, null, shop.getName(), shop.getType(), shop.getPhone(), shop.getEmail(),
                shop.getGSTIN(), shop.getOwner());
    }

    public List<Shop> getAllShops() {
        final String sql = "SELECT * FROM shops";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public List<Shop> getAllShopsUnder(String username) {
        final String sql = "SELECT * FROM shops where owner=?";
        return jdbcTemplate.query(sql, userRowMapper, username);
    }

    // TODO: Fix phone
    private RowMapper<Shop> userRowMapper = new RowMapper<Shop>() {
        @Override
        public Shop mapRow(ResultSet row, int i) throws SQLException {
            return new Shop(row.getInt("id"), row.getString("name"), row.getString("owner"), row.getString("type"),
                    null, row.getString("email"), row.getString("gstin"));
        }
    };
}
