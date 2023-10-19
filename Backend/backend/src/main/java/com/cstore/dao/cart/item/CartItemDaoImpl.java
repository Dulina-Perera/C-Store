package com.cstore.dao.cart.item;

import com.cstore.model.cart.CartItem;
import com.cstore.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartItemDaoImpl implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CartItem> findByUserId(Long userId) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"cart_item\" " +
                     "WHERE \"user_id\" = ?;";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, userId),
            new BeanPropertyRowMapper<>(CartItem.class)
        );

    }

    @Override
    public Optional<CartItem> findByUIdAndVId(Long userId, Long variantId) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"cart_item\" " +
                     "WHERE (\"user_id\", \"variant_id\") = (?, ?);";

        try {
            CartItem item = jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(CartItem.class),
                userId,
                variantId
            );

            return Optional.of(item);
        } catch (NullPointerException e) {
            return Optional.empty();
        }

    }

    @Override
    public int updateCount(Long userId, Long variantId, Integer count) throws DataAccessException {

        String sql = "UPDATE \"cart_item\" " +
                     "SET \"count\" = ? " +
                     "WHERE (\"user_id\", \"variant_id\") = (?, ?);";

        return jdbcTemplate.update(
            sql,
            preparedStatement -> {
                preparedStatement.setInt(1, count);
                preparedStatement.setLong(2, userId);
                preparedStatement.setLong(3, variantId);
            }
        );

    }

    @Override
    public int deleteItem(Long userId, Long variantId) throws DataAccessException {
        String sql = "DELETE FROM \"cart_item\" " +
                     "WHERE (\"user_id\", \"variant_id\") = (?, ?);";

        return jdbcTemplate.update(
            sql,
            preparedStatement -> {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, variantId);
            }
        );
    }

}
