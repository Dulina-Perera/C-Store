package com.cstore.dao.product;

import com.cstore.model.product.Product;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> findProduct(Product unknown) throws SQLException;

    List<Product> findAll();

    Optional<Product> findById(Long productId);

    List<Product> findByName(String productName) throws SQLException;

    Product save(Product product);

    List<Product> findByCategoryId(Long categoryId) throws DataAccessException;

    Integer countStocks(Long productId) throws DataAccessException;
}
