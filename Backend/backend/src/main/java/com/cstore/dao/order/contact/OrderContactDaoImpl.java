package com.cstore.dao.order.contact;

import com.cstore.model.order.Order;
import com.cstore.model.order.OrderContact;
import com.cstore.model.order.OrderContactId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderContactDaoImpl implements OrderContactDao {
    String url = "jdbc:mysql://localhost:3306/cstore";
    String username = "cadmin";
    String password = "cstore_GRP28_CSE21";

    Logger logger = LoggerFactory.getLogger(OrderContactDaoImpl.class);

    @Override
    public List<OrderContact> findByOrder(Order order) {
        List<OrderContact> orderContacts = new ArrayList<OrderContact>();
        String sql = "SELECT * " +
                     "FROM order_contact " +
                     "WHERE order_id = ?;";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, order.getOrderId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderContactId orderContactId = new OrderContactId();
                    OrderContact orderContact = new OrderContact();

                    orderContactId.setOrderId(order.getOrderId());
                    orderContactId.setTelephoneNumber(resultSet.getInt("telephone_number"));

                    orderContact.setOrderContactId(orderContactId);
                    orderContact.setOrder(order);

                    orderContacts.add(orderContact);
                }

                return orderContacts;
            }
        } catch (SQLException sqe) {
            logger.error("Error while fetching order contacts from the database.", sqe);
            return new ArrayList<>();
        }
    }
}
