package com.cstore.dao.report;

import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface ReportDao {
    Optional<SalesReport> findSalesReport(Short year, Short quarter) throws DataAccessException;

    List<SalesItem> findSalesItems(Short year, Short quarter) throws DataAccessException;
}
