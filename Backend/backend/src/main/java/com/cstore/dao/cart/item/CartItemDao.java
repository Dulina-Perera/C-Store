package com.cstore.dao.cart.item;

import com.cstore.model.cart.CartItem;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface CartItemDao {
    List<CartItem> findByUserId(Long userId) throws DataAccessException;

    Optional<CartItem> findByUIdAndVId(Long userId, Long variantId) throws DataAccessException;

    int updateCount(Long userId, Long variantId, Integer count);

    int deleteItem(Long userId, Long variantId);
}
