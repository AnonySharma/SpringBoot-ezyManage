package com.ankit.ezymanage.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.User;

import org.springframework.jdbc.core.RowMapper;

public final class RowMappers {
    private static boolean isValid(String s) {
        return ((s != null) && (s != ""));
    }

    public static RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet row, int i) throws SQLException {
            User user = new User();
            user.setId(row.getInt("id"));
            user.setUsername(row.getString("username"));
            user.setPassword(row.getString("password"));
            user.setRole(row.getString("role"));
            user.setAdmin(row.getBoolean("isadmin"));
            return user;
        }
    };

    // TODO: Fix phone
    public static RowMapper<Shop> shopRowMapper = new RowMapper<Shop>() {
        @Override
        public Shop mapRow(ResultSet row, int i) throws SQLException {
            Shop shop = new Shop();
            shop.setId(row.getInt("id"));
            shop.setName(row.getString("name"));
            shop.setType(row.getString("type"));
            shop.setPhone(null);
            shop.setEmail(row.getString("email"));
            shop.setGSTIN(row.getString("gstin"));
            shop.setOwner(row.getString("owner"));
            return shop;
        }
    };

    public static RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet row, int i) throws SQLException {
            Product product = new Product();
            product.setId(row.getInt("id"));
            product.setName(row.getString("name"));
            product.setImage(row.getString("image"));
            product.setDescription(row.getString("description"));
            product.setPrice(row.getInt("price"));
            return product;
        }
    };

    public static RowMapper<Profile> profileRowMapper = new RowMapper<Profile>() {
        @Override
        public Profile mapRow(ResultSet row, int i) throws SQLException {
            Profile profile = new Profile();

            if (isValid(row.getString("username"))) {
                profile.setUsername(row.getString("username"));
            }

            if (isValid(row.getString("firstname"))) {
                profile.setFirstName(row.getString("firstname"));
            }

            if (isValid(row.getString("middlename"))) {
                profile.setMiddleName(row.getString("middlename"));
            }

            if (isValid(row.getString("lastname"))) {
                profile.setLastName(row.getString("middlename"));
            }

            if (isValid(row.getString("phone"))) {
                profile.setPhoneNumber(row.getString("phone") != null && row.getString("phone") != ""
                        ? Long.parseLong(row.getString("phone"))
                        : null);
            }

            if (isValid(row.getString("gender"))) {
                profile.setGender(row.getString("gender").charAt(0));
            }

            if (isValid(row.getString("dob"))) {
                System.out.println(row.getString("dob"));
                try {
                    profile.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(row.getString("dob")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (isValid(row.getString("email"))) {
                profile.setEmail(row.getString("email"));
            }

            if (isValid(row.getString("address"))) {
                profile.setAddress(row.getString("address"));
            }

            if (isValid(row.getString("aadhaar"))) {
                profile.setAadhaarNumber(Long.parseLong(row.getString("aadhaar")));
            }

            return profile;
        }
    };

    public static RowMapper<Cart> cartRowMapper = new RowMapper<Cart>() {
        @Override
        public Cart mapRow(ResultSet row, int i) throws SQLException {
            Cart cart = new Cart();
            cart.setId(row.getInt("id"));
            cart.setDate(row.getString("date"));
            cart.setShopId(row.getInt("shop_id"));
            cart.setCustomerId(row.getInt("customer_id"));
            cart.setTotal(row.getInt("total"));
            return cart;
        }
    };

    public static RowMapper<Pair<Integer, Integer>> cartItemRowMapper = new RowMapper<Pair<Integer, Integer>>() {
        @Override
        public Pair<Integer, Integer> mapRow(ResultSet row, int i) throws SQLException {
            Pair<Integer, Integer> pair = new Pair<Integer, Integer>();
            pair.setFirst(row.getInt("product_id"));
            pair.setSecond(row.getInt("quantity"));
            return pair;
        }
    };

    public static RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet row, int i) throws SQLException {
            Order order = new Order();
            order.setOrderId(row.getInt("order_id"));
            order.setShopId(row.getInt("shop_id"));
            order.setStaffId(row.getInt("staff_id"));
            order.setCustomerId(row.getInt("customer_id"));
            order.setDate(row.getString("order_date"));
            order.setMode(row.getString("mode"));
            order.setStatus(row.getString("status"));
            return order;
        }
    };

    public static RowMapper<Pair<Integer, Integer>> orderItemRowMapper = new RowMapper<Pair<Integer, Integer>>() {
        @Override
        public Pair<Integer, Integer> mapRow(ResultSet row, int i) throws SQLException {
            Pair<Integer, Integer> pair = new Pair<Integer, Integer>();
            pair.setFirst(row.getInt("product_id"));
            pair.setSecond(row.getInt("quantity"));
            return pair;
        }
    };
}
