package com.cstore.dao.category;

import com.cstore.model.category.Category;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository

@RequiredArgsConstructor
public class CategoryDaoImpl implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() throws DataAccessException {
        String sql = "SELECT * " +
                "FROM category;";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Category.class)
        );
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        try {
            String sql = "SELECT * FROM category WHERE category_id = ?";

            Category category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), categoryId);
            return Optional.ofNullable(category);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Category> findCategory(Category unknown) {
        try {
            String sql = "SELECT * " +
                         "FROM \"category\" " +
                         "WHERE (\"category_name\", \"category_description\") = (?, ?);";

            Category category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), unknown.getCategoryName(), unknown.getCategoryDescription());
            return Optional.ofNullable(category);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Category category) {
        String sql = "INSERT INTO \"category\" (\"category_name\", \"category_description\") " +
                     "VALUES(?, ?) " +
                     "RETURNING \"category_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, category.getCategoryName());
                ps.setString(2, category.getCategoryDescription());

                return ps;
            },
            keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    @Override
    public Category update(Category category) {
        String sql = "UPDATE \"category\" " +
                     "SET \"category_name\" = ?, \"category_description\" = ? " +
                     "WHERE \"category_id\" = ? " +
                     "RETURNING *;";

        return jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Category.class),
            category.getCategoryName(),
            category.getCategoryDescription(),
            category.getCategoryId()
        );
    }

    @Override
    public Category delete(Long categoryId) {

        String sql = "WITH deleted_category AS ( " +
                     "    DELETE FROM \"category\" " +
                     "    WHERE \"category_id\" = ? " +
                     "    RETURNING * " +
                     ") " +
                     "SELECT * " +
                     "FROM deleted_category";

        return jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Category.class),
            categoryId
        );

    }

    @Override
    public List<Category> findByProductId(
        Long productId
    ) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"categories_from_product\"(?);";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, productId),
            new BeanPropertyRowMapper<>(Category.class)
        );

    }

    @Override
    public List<Category> findAllRootCategories(
    ) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"root_category\";";

        return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(Category.class)
        );

    }

    @Override
    public List<Category> findAllDirectSubCategories(
        Long categoryId
    ) throws DataAccessException {

        String sql = "SELECT * " +
                     "FROM \"category\" " +
                     "WHERE \"category_id\" IN (SELECT \"sub_category_id\" " +
                                               "FROM \"sub_category\" " +
                                               "WHERE \"category_id\" = ?);";

        return jdbcTemplate.query(
            sql,
            preparedStatement -> preparedStatement.setLong(1, categoryId),
            new BeanPropertyRowMapper<>(Category.class)
        );

    }

}
