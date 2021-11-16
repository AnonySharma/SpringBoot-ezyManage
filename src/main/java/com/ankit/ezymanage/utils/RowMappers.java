package com.ankit.ezymanage.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.model.OwnerRequest;
import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.Staff;
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
            // row.getTime(arg0)
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
                profile.setPhoneNumber(row.getString("phone"));
            }

            if (isValid(row.getString("gender"))) {
                profile.setGender(row.getString("gender").charAt(0));
            }

            if (isValid(row.getString("dob"))) {
                System.out.println(row.getDate("dob"));
            }

            if (isValid(row.getString("email"))) {
                profile.setEmail(row.getString("email"));
            }

            if (isValid(row.getString("address"))) {
                profile.setAddress(row.getString("address"));
            }

            if (isValid(row.getString("aadhaar"))) {
                profile.setAadhaarNumber(row.getString("aadhaar"));
            }

            return profile;
        }
    };

    public static RowMapper<Cart> cartRowMapper = new RowMapper<Cart>() {
        @Override
        public Cart mapRow(ResultSet row, int i) throws SQLException {
            Cart cart = new Cart();
            cart.setId(row.getInt("id"));
            cart.setShopId(row.getInt("shop_id"));
            cart.setCustomerId(row.getInt("customer_id"));
            cart.setTotal(row.getInt("total"));

            Timestamp time = row.getTimestamp("date");
            cart.setDate(new Date(time.getTime()));
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
            order.setTotal(row.getInt("total"));
            order.setCustomerId(row.getInt("customer_id"));
            order.setMode(row.getString("mode"));
            order.setStatus(row.getString("status"));

            Timestamp time = row.getTimestamp("order_date");
            System.out.println(time);
            order.setDate(new Date(time.getTime()));
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

    public static RowMapper<Staff> staffRowMapper = new RowMapper<Staff>() {
        @Override
        public Staff mapRow(ResultSet row, int i) throws SQLException {
            Staff staff = new Staff();
            staff.setStaffId(row.getInt("staff_id"));
            staff.setName(row.getString("staff_name"));
            staff.setShopId(row.getInt("shop_id"));
            staff.setDateOfJoining(row.getTimestamp("date_of_joining"));
            staff.setDesignation(row.getString("designation"));
            staff.setSalary(row.getInt("salary"));
            return staff;
        }
    };

    public static RowMapper<OwnerRequest> ownerRequestRowMapper = new RowMapper<OwnerRequest>() {
        @Override
        public OwnerRequest mapRow(ResultSet row, int i) throws SQLException {
            OwnerRequest ownerRequest = new OwnerRequest();
            ownerRequest.setId(row.getInt("id"));
            ownerRequest.setUserId(row.getInt("user_id"));
            ownerRequest.setDate(row.getTimestamp("date"));
            ownerRequest.setStatus(row.getString("status"));
            return ownerRequest;
        }
    };
}
