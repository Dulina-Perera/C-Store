package com.cstore.dao.report;

import com.cstore.domain.report.Product;
import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
                     "WHERE (\"year\", \"quarter\" = ?, ?);";

        return Optional.ofNullable(jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(SalesReport.class),
            year,
            quarter
        ));
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
        Timestamp till
    ) throws DataAccessException {
        String sql = "SELECT p.\"product_id\", p.\"product_name\", p.\"image_url\", SUM(oi.\"count\") AS \"sales\" " +
                     "FROM \"order\" AS o NATURAL LEFT OUTER JOIN " +
                     "     \"order_item\" AS oi NATURAL LEFT OUTER JOIN " +
                     "     (SELECT DISTINCT \"product_id\", \"variant_id\" " +
                     "      FROM \"varies_on\") AS vo NATURAL LEFT OUTER JOIN " +
                     "     \"product\" AS p " +
                     "WHERE o.\"date\" BETWEEN ? AND ? " +
                     "GROUP BY p.\"product_id\", p.\"product_name\", p.\"image_url\";";

        return jdbcTemplate.query(
            sql,
            ps -> {
                ps.setTimestamp(1, from);
                ps.setTimestamp(2, till);
            },
            new BeanPropertyRowMapper<>(Product.class)
        );
    }
}
