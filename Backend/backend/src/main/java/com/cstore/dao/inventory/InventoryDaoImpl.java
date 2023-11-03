package com.cstore.dao.inventory;

import com.cstore.model.warehouse.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryDaoImpl implements InventoryDao {
    private final JdbcTemplate templ;

    @Override
    public Optional<Inventory> findByPrimaryKey(
        Long warehouseId,
        Long variantId
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"inventory\" " +
                     "WHERE \"warehouse_id\" = ? AND \"variant_id\" = ?;";

        Inventory inventory = templ.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(Inventory.class),
            warehouseId,
            variantId
        );
        return Optional.ofNullable(inventory);
    }

    @Override
    public Integer findCountByVariantId(Long variantId) {
        String sql = "SELECT SUM(\"quantity\") " +
                     "FROM \"inventory\" " +
                     "WHERE \"variant_id\" = ? ";

        return templ.queryForObject(
            sql,
            Integer.class,
            variantId
        );
    }

    @Override
    public int stock(
        Inventory newStock
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        String sql = "CALL \"stock\"(?, ?, ?, ?);";

        return templ.update(
            sql,
            ps -> {
                ps.setLong(1, newStock.getWarehouseId());
                ps.setLong(2, newStock.getVariantId());
                ps.setString(3, newStock.getSku());
                ps.setInt(4, newStock.getQuantity());
            }
        );
    }

    @Override
    public void updateInventory(
        Long userId,
        Long orderId
    ) throws DataAccessException, SQLIntegrityConstraintViolationException {
        String sql = "CALL \"update_inventory\"(?, ?);";

        templ.update(
            sql,
            ps -> {
                ps.setLong(1, userId);
                ps.setLong(2, orderId);
            }
        );
    }
}
