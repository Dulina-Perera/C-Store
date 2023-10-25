package com.cstore.dao.order;

import com.cstore.model.order.Order;
import com.cstore.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public void deleteByID(Long orderId) throws SQLException {
        String sql = "DELETE FROM `order`" +
                     "WHERE `order_id` = ?";

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, orderId);
        preparedStatement.executeUpdate();
    }
}
