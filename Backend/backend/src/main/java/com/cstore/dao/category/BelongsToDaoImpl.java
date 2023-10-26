package com.cstore.dao.category;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;

@Repository
@RequiredArgsConstructor
public class BelongsToDaoImpl implements BelongsToDao {
    private final JdbcTemplate templ;

    @Override
    public void save(
        Long productId,
        Long categoryId
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO \"belongs_to\" " +
                     "VALUES (?, ?);";

        templ.update(
            sql,
            ps -> {
                ps.setLong(1, categoryId);
                ps.setLong(2, productId);
            }
        );
    }
}
