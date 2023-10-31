package com.cstore.dao.image;

import com.cstore.model.product.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository

@RequiredArgsConstructor
public class ImageDaoImpl implements ImageDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Image> findByProductId(Long productId) {
        String sql = "SELECT * " +
                     "FROM \"images_from_product\"(?);";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, productId),
            new BeanPropertyRowMapper<>(Image.class)
        );
    }

    @Override
    public Image save(
        Image image
    ) throws DataAccessException {
        String sql = "INSERT INTO \"image\"(\"product_id\", \"url\") " +
                     "VALUES(?, ?);";

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);

                ps.setLong(1, image.getProductId());
                ps.setString(2, image.getUrl());

                return ps;
            }
        );

        return image;
    }
}
