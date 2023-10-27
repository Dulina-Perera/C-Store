package com.cstore.dao.report;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportDaoImpl implements ReportDao {
    private final JdbcTemplate temp;
}
