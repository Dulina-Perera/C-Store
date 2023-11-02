package com.cstore.dao.cart.item;

import com.cstore.domain.cart.update.CartItemResponse;
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
    public List<CartItemResponse> findByUserId(
        Long userId
    ) throws DataAccessException {
        String sql = "SELECT DISTINCT ci.\"variant_id\", p.\"product_name\", p.\"main_image\", v.\"price\" AS \"variant_price\", ci.\"quantity\" " +
                     "FROM \"cart_item\" AS ci NATURAL LEFT OUTER JOIN " +
                     "     \"variant\" AS v NATURAL LEFT OUTER JOIN " +
                     "     \"varies_on\" AS vo NATURAL LEFT OUTER JOIN " +
                     "     \"product\" AS p " +
                     "WHERE ci.\"user_id\" = ?;";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, userId),
            new BeanPropertyRowMapper<>(CartItemResponse.class)
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
    public int updateCount(
        Long userId,
        Long variantId,
        Integer quantity
    ) throws DataAccessException {
        String sql = "UPDATE \"cart_item\" " +
                     "SET \"quantity\" = ? " +
                     "WHERE (\"user_id\", \"variant_id\") = (?, ?);";

        return jdbcTemplate.update(
            sql,
            preparedStatement -> {
                preparedStatement.setInt(1, quantity);
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
