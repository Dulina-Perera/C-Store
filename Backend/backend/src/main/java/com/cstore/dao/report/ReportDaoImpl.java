package com.cstore.dao.report;

import com.cstore.domain.report.Category;
import com.cstore.domain.report.Product;
import com.cstore.domain.report.Quarter;
import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportDaoImpl implements ReportDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<SalesReport> findSalesReport(
        Short year,
        Short quarter
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"sales_report\" " +
                     "WHERE (\"year\", \"quarter\") = (?, ?);";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(SalesReport.class),
                year,
                quarter
            ));
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    @Override
    public List<SalesItem> findSalesItems(
        Short year,
        Short quarter
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"sales_item\" " +
                     "WHERE (\"year\", \"quarter\") = (?, ?);";

        return jdbcTemplate.query(
            sql,
            ps -> {
                ps.setShort(1, year);
                ps.setShort(2, quarter);
            },
            new BeanPropertyRowMapper<>(SalesItem.class)
        );
    }

    @Override
    public List<Product> findProductsWithMostSales(
        Timestamp from,
        Timestamp till,
        Short limit
    ) throws DataAccessException {
        String sql = "SELECT p.\"product_id\", p.\"product_name\", p.\"main_image\", SUM(oi.\"quantity\") AS \"sales\" " +
                     "FROM \"order\" AS o NATURAL LEFT OUTER JOIN " +
                     "     \"order_item\" AS oi NATURAL LEFT OUTER JOIN " +
                     "     (SELECT DISTINCT \"product_id\", \"variant_id\" " +
                     "      FROM \"varies_on\") AS vo NATURAL LEFT OUTER JOIN " +
                     "     \"product\" AS p " +
                     "WHERE o.\"date\" BETWEEN ? AND ? " +
                     "GROUP BY p.\"product_id\", p.\"product_name\", p.\"main_image\" " +
                     "LIMIT ?;";

        return jdbcTemplate.query(
            sql,
            ps -> {
                ps.setTimestamp(1, from);
                ps.setTimestamp(2, till);
                ps.setShort(3, limit);
            },
            new BeanPropertyRowMapper<>(Product.class)
        );
    }

    @Override
    public List<Category> findCategoriesWithMostOrders(
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"categories_with_most_orders\"();";

        return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(Category.class)
        );
    }

    @Override
    public List<Quarter> findQuartersWithMostInterest(
        Long productId
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"quarters_with_most_interest\"(?);";

        return jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, productId),
            new BeanPropertyRowMapper<>(Quarter.class)
        );
    }

    @Override
    public int saveQuarterlySalesReport(
        Timestamp quarterStart,
        Timestamp quarterEnd
    ) throws DataAccessException {
        String sql = "CALL \"quarterly_sales_report\"(?, ?);";

        return jdbcTemplate.update(
            sql,
            preparedStatement -> {
                preparedStatement.setTimestamp(1, quarterStart);
                preparedStatement.setTimestamp(2, quarterEnd);
            }
        );
    }
}
