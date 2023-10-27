package com.cstore.dao.cart.item;

import com.cstore.domain.cart.update.CartItemResponse;
import com.cstore.model.cart.CartItem;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface CartItemDao {
    List<CartItemResponse> findByUserId(Long userId) throws DataAccessException;

    Optional<CartItem> findByUIdAndVId(Long userId, Long variantId) throws DataAccessException;

    int updateCount(Long userId, Long variantId, Integer count);

    int deleteItem(Long userId, Long variantId);
}
