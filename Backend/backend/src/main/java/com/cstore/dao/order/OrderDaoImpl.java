package com.cstore.dao.order;

import com.cstore.dto.order.OrderDetailsDto;
import com.cstore.model.order.Order;
import com.cstore.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private final JdbcTemplate templ;

    String url = "jdbc:mysql://localhost:3306/cstore";
    String username = "cadmin";
    String password = "cstore_GRP28_CSE21";

    Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public List<Order> findAll(
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"order\";";

        return templ.query(
            sql,
            new BeanPropertyRowMapper<>(Order.class)
        );
    }

    @Override
    public Optional<Order> findById(
        Long orderId
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"order\" " +
                     "WHERE \"order_id\" = ?;";

        try {
            Order order = templ.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Order.class),
                orderId
            );

            return Optional.of(order);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM `order`;";

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void deleteByID(
        Long orderId
    ) throws DataAccessException {
        String sql = "DELETE FROM \"order\" " +
                     "WHERE \"order_id\" = ?";

        templ.update(
            sql,
            ps -> ps.setLong(1, orderId)
        );
    }

    @Override
    public Long placeOrder(
        Long userId
    ) {
        String sql = "INSERT INTO \"order\" (\"status\", \"customer_id\") " +
                     "VALUES ('PLACED', ?) " +
                     "RETURNING \"order_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        templ.update(
            conn -> {
                PreparedStatement ps = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
                );

                ps.setLong(1, userId);

                return ps;
            },
            keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    @Override
    public Long confirmOrder(
        Long orderId,
        OrderDetailsDto orderDetails
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        String sql = "UPDATE \"order\" " +
                     "SET \"status\" = 'PROCESSING', " +
                     "    \"date\" = ?, \"total_payment\" = ?, \"payment_method\" = ?, \"delivery_method\" = ?, " +
                     "    \"email\" = ?, \"street_number\" = ?, \"street_name\" = ?, \"city\" = ?, \"zipcode\" = ?, " +
                     "    \"telephone_number\" = ?" +
                     "WHERE \"order_id\" = ? " +
                     "RETURNING \"order_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        templ.update(
            conn -> {
                PreparedStatement ps = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
                );

                ps.setTimestamp(1, orderDetails.getDate());
                ps.setBigDecimal(2, orderDetails.getTotalPayment());
                ps.setString(3, orderDetails.getPaymentMethod());
                ps.setString(4, orderDetails.getDeliveryMethod());

                ps.setString(5, orderDetails.getEmail());
                ps.setString(6, orderDetails.getStreetNumber());
                ps.setString(7, orderDetails.getStreetName());
                ps.setString(8, orderDetails.getCity());
                ps.setInt(9, orderDetails.getZipcode());

                ps.setString(10, orderDetails.getTelephoneNumber());

                ps.setLong(11, orderId);

                return ps;
            },
            keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    @Override
    public void emptyCart(Long orderId) throws DataAccessException, SQLIntegrityConstraintViolationException {
        String sql = "DELETE " +
                     "FROM \"cart_item\" " +
                     "WHERE \"user_id\" = (" +
                     "    SELECT \"customer_id\" " +
                     "    FROM \"order\" " +
                     "    WHERE \"order_id\" = ?" +
                     ");";

        templ.update(
            sql,
            ps -> ps.setLong(1, orderId)
        );
    }
}
