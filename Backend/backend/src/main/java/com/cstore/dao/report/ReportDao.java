package com.cstore.dao.report;

import com.cstore.domain.report.Category;
import com.cstore.domain.report.Product;
import com.cstore.domain.report.Quarter;
import com.cstore.model.report.SalesItem;
import com.cstore.model.report.SalesReport;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReportDao {
    Optional<SalesReport> findSalesReport(Short year, Short quarter) throws DataAccessException;

    List<SalesItem> findSalesItems(Short year, Short quarter) throws DataAccessException;

    List<Product> findProductsWithMostSales(Timestamp from, Timestamp till, Short limit) throws DataAccessException;

    List<Category> findCategoriesWithMostOrders() throws DataAccessException;

    List<Quarter> findQuartersWithMostInterest(Long productId) throws DataAccessException;

    int saveQuarterlySalesReport(Timestamp quarterStart, Timestamp quarterEnd) throws DataAccessException;
}
