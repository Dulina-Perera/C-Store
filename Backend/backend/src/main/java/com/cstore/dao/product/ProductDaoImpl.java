package com.cstore.dao.product;

import com.cstore.model.category.Category;
import com.cstore.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
    private final JdbcTemplate jdbcTemplate;

    String url = "jdbc:mysql://localhost:3306/cstore";
    String username = "cadmin";
    String password = "cstore_GRP28_CSE21";

    Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    @Override
    public Optional<Product> findProduct(Product unknown) throws SQLException {
        String sql = "SELECT * " +
                     "FROM `product` " +
                     "WHERE `product_name` = ? AND `base_price` = ? AND `brand` = ? AND `description` = ? AND `image_url` = ?;";

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, unknown.getProductName());
        preparedStatement.setBigDecimal(2, unknown.getBasePrice());
        preparedStatement.setString(3, unknown.getBrand());
        preparedStatement.setString(4, unknown.getDescription());
        preparedStatement.setString(5, unknown.getImageUrl());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            unknown.setProductId(resultSet.getLong("category_id"));

            return Optional.of(unknown);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            List<Product> products = new ArrayList<Product>();
            String sql = "SELECT * " +
                         "FROM `product`;";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Product product = new Product();

                product.setProductId(resultSet.getLong("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setBasePrice(resultSet.getBigDecimal("base_price"));
                product.setBrand(resultSet.getString("brand"));
                product.setDescription(resultSet.getString("description"));
                product.setImageUrl(resultSet.getString("image_url"));

                products.add(product);
            }

            return products;
        }
        catch (SQLException sqe) {
            logger.error("Error while fetching data.", sqe);
            return new ArrayList<>();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Error closing Result Set.", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Error closing Statement.", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing Connection.", e);
                }
            }
        }
    }

    @Override

    @Comment("This method is perfect.")
    public Optional<Product> findById(Long productId) {
        String sql = "SELECT * " +
                     "FROM \"product\" " +
                     "WHERE \"product_id\" = ?;";

        try {
            Product product = jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Product.class),
                productId
            );

            return Optional.of(product);
        } catch (DataAccessException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByName(String productName) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * " +
                     "FROM `product` " +
                     "WHERE `product_name` LIKE ?;";

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "%" + productName + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Product product = new Product();

            product.setProductId(resultSet.getLong("product_id"));
            product.setProductName(resultSet.getString("product_name"));
            product.setBasePrice(resultSet.getBigDecimal("base_price"));
            product.setBrand(resultSet.getString("brand"));
            product.setDescription(resultSet.getString("description"));
            product.setImageUrl(resultSet.getString("image_url"));

            products.add(product);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return products;
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO \"product\"(\"product_name\", \"base_price\", \"brand\", \"description\", \"image_url\") " +
                     "VALUES(?, ?, ?, ?, ?) " +
                     "RETURNING \"product_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, product.getProductName());
                ps.setBigDecimal(2, product.getBasePrice());
                ps.setString(3, product.getBrand());
                ps.setString(4, product.getDescription());
                ps.setString(5, product.getImageUrl());

            return ps;
            },
            keyHolder
        );

        Number generatedUserId = keyHolder.getKey();

        if (generatedUserId != null) {
            product.setProductId(generatedUserId.longValue());
        }

        return product;
    }

    @Override
    public List<Product> findByCategoryId(
        Long categoryId
    ) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"products_from_category\"(?);";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, categoryId),
            new BeanPropertyRowMapper<>(Product.class)
        );

    }

    @Override
    public Integer countStocks(
        Long productId
    ) throws DataAccessException {

        String sql = "SELECT \"count_stocks\"(?);";

        return jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            productId
        );
    }

}
