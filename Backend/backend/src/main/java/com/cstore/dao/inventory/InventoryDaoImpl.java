package com.cstore.dao.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InventoryDaoImpl implements InventoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer findCountByVariantId(Long variantId) {
        String sql = "SELECT SUM(\"count\") " +
                     "FROM \"inventory\" " +
                     "WHERE \"variant_id\" = ? ";

        return jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            variantId
        );
    }
}
