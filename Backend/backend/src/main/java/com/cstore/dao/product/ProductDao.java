package com.cstore.dao.product;

import com.cstore.model.product.Product;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> findProduct(Product unknown) throws SQLException;

    List<Product> findAll() throws DataAccessException;

    Optional<Product> findById(Long productId) throws DataAccessException;

    List<Product> findByName(String productName) throws DataAccessException;

    Product save(Product product);

    List<Product> findByCategoryId(Long categoryId) throws DataAccessException;

    Integer countStocks(Long productId) throws DataAccessException;
}
