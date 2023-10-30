package com.cstore.dao.order;

import com.cstore.domain.report.OrderItemProperty;
import com.cstore.dto.order.OrderDetailsDto;
import com.cstore.model.order.Order;
import com.cstore.model.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private final JdbcTemplate templ;

    @Override
    public List<com.cstore.model.order.Order> findAll(
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"order\";";

        return templ.query(
            sql,
            new BeanPropertyRowMapper<>(com.cstore.model.order.Order.class)
        );
    }

    @Override
    public List<OrderItem> findOrderItems(Long orderId) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"order_item\" " +
                     "WHERE \"order_id\" = ?;";

        return templ.query(
            sql,
            ps -> ps.setLong(1, orderId),
            new BeanPropertyRowMapper<>(OrderItem.class)
        );
    }

    @Override
    public List<OrderItemProperty> findAllOrderItems(
        Long orderId
    ) throws DataAccessException {
        String sql = "SELECT oi.\"variant_id\", " +
                     "       pd.\"product_name\", " +
                     "       pr.\"property_name\", " +
                     "       pr.\"value\", " +
                     "       oi.\"count\" AS \"quantity\", " +
                     "       oi.\"price\" " +
                     "FROM \"order_item\" AS oi NATURAL LEFT OUTER JOIN " +
                     "     \"varies_on\" AS vo NATURAL LEFT OUTER JOIN " +
                     "     \"property\" AS pr NATURAL LEFT OUTER JOIN " +
                     "     \"product\" AS pd " +
                     "WHERE \"order_id\" = ?;";

        return templ.query(
            sql,
            ps -> ps.setLong(1, orderId),
            new BeanPropertyRowMapper<>(OrderItemProperty.class)
        );
    }

    @Override
    public List<Order> findProcessedOrders(
        Long customerId
    ) throws DataAccessException {
        String sql = "SELECT \"order_id\", \"date\", \"total_payment\", \"payment_method\", \"delivery_method\" " +
                     "FROM \"order\" " +
                     "WHERE \"customer_id\" = ? AND \"status\" = 'PROCESSED';";

        return templ.query(
            sql,
            ps -> ps.setLong(1, customerId),
            new BeanPropertyRowMapper<>(Order.class)
        );
    }

    @Override
    public List<Order> findProcessingAndProcessedOrders(
        Long customerId
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"order\" " +
                     "WHERE \"customer_id\" = ? AND \"status\" IN ('PROCESSING', 'PROCESSED');";

        return templ.query(
            sql,
            ps -> ps.setLong(1, customerId),
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
            com.cstore.model.order.Order order = templ.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(com.cstore.model.order.Order.class),
                orderId
            );

            return Optional.of(order);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
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
        String sql = "INSERT INTO \"order\" (\"status\", \"date\", \"customer_id\") " +
                     "VALUES ('PLACED', CURRENT_TIMESTAMP, ?) " +
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

    @Override
    public int deleteTimedOutOrders(
    ) throws DataAccessException {
        String sql = "DELETE " +
                     "FROM \"order\" " +
                     "WHERE \"status\" = 'PLACED' AND EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - \"date\")) / 60 > 1;";

        return templ.update(sql);
    }
}
