package com.cstore.dao.cart;

import org.springframework.dao.DataAccessException;

public interface CartDao {
    int addToCart(Long userId, Long variantId, Integer count) throws DataAccessException;

    int removeFromCart(Long userId, Long variantId);
}
