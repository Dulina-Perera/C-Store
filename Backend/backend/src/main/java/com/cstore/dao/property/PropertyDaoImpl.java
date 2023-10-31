package com.cstore.dao.property;

import com.cstore.model.product.Product;
import com.cstore.model.product.Property;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
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
@Comment("This class is perfect.")
public class PropertyDaoImpl implements PropertyDao {
    private final JdbcTemplate jdbcTemplate;

    String url = "jdbc:mysql://localhost:3306/cstore";
    String username = "cadmin";
    String password = "cstore_GRP28_CSE21";

    @Override
    public Optional<Property> findById(Long propertyId) {
        String sql = "SELECT * " +
                     "FROM \"property\" " +
                     "WHERE \"property_id\" = ?;";

        try {
            Property property = jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Property.class),
                propertyId
            );

            return Optional.of(property);
        } catch (DataAccessException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Property> findByProductId(
        Long productId
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"properties_from_product\"(?);";

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setLong(1, productId),
                new BeanPropertyRowMapper<>(Property.class)
        );
    }

    @Override
    public List<Property> findByVariantId(Long variantId) throws DataAccessException {
        String sql = "SELECT p.* " +
                     "FROM \"property\" AS p NATURAL LEFT OUTER JOIN " +
                     "     \"varies_on\" AS vo " +
                     "WHERE vo.\"variant_id\" = ?;";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, variantId),
            new BeanPropertyRowMapper<>(Property.class)
        );
    }

    @Override
    public Property save(Property property) {
        String sql = "INSERT INTO \"property\"(\"property_name\", \"value\", \"url\", \"price_increment\") " +
                     "VALUES(?, ?, ?, ?) " +
                     "RETURNING \"property_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, property.getPropertyName());
                ps.setString(2, property.getValue());
                ps.setString(3, property.getImageUrl());
                ps.setBigDecimal(4, property.getPriceIncrement());

                return ps;
            },
            keyHolder
        );

        Number generatedUserId = keyHolder.getKey();

        if (generatedUserId != null) {
            property.setPropertyId(generatedUserId.longValue());
        }

        return property;
    }

    @Override
    public List<Property> findUnmarketableProperties(
        Long productId
    ) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"unmarketable_properties\"(?)";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, productId),
            new BeanPropertyRowMapper<>(Property.class)
        );

    }
}
