package com.cstore.dao.report;

import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import lombok.RequiredArgsConstructor;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
