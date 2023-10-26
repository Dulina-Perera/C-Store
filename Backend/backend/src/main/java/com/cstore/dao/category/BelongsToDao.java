package com.cstore.dao.category;

import org.springframework.dao.DataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;

public interface BelongsToDao {
    void save(Long productId, Long categoryId) throws DataAccessException, SQLIntegrityConstraintViolationException;
}
