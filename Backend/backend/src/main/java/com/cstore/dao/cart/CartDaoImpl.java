package com.cstore.dao.cart;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CartDaoImpl implements CartDao {
    private final JdbcTemplate jdbcTemplate;

    public CartDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addToCart(
        Long userId,
        Long variantId,
        Integer quantity
    ) throws DataAccessException {

        String sql = "CALL \"add_to_cart\"(?, ?, ?);";

        return jdbcTemplate.update(
            sql,
            preparedStatement -> {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, variantId);
                preparedStatement.setInt(3, quantity);
            }
        );

    }

    @Override
    public int removeFromCart(Long userId, Long variantId) {
        String sql = "DELETE " +
                     "FROM cart_item " +
                     "WHERE (user_id, variant_id) = (?, ?);";

        return jdbcTemplate.update(sql, userId, variantId);
    }
}
