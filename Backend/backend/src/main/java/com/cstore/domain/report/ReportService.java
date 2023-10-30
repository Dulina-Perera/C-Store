package com.cstore.domain.report;

import com.cstore.dao.report.ReportDao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static com.cstore.domain.report.Period.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportDao reportDao;

    public List<Product> getProductsWithMostSales(
        Period period
    ) throws DataAccessException {
        Timestamp from, till;

        LocalDate now = LocalDate.now();
        switch (period) {
            case TODAY, YESTERDAY -> {
                LocalDate current = (period == TODAY) ? now : now.minusDays(1);
                from = Timestamp.valueOf(current.atStartOfDay());
                till = Timestamp.valueOf(current.plusDays(1).atStartOfDay());
            }
            case THIS_WEEK, LAST_WEEK -> {
                LocalDate current = (period == THIS_WEEK) ? now : now.minusWeeks(1);
                from = Timestamp.valueOf(current.with(java.time.DayOfWeek.MONDAY).atStartOfDay());
                till = Timestamp.valueOf(current.with(java.time.DayOfWeek.SUNDAY).plusDays(1).atStartOfDay());
            }
            case THIS_MONTH, LAST_MONTH -> {
                LocalDate current = (period == THIS_MONTH) ? now : now.minusMonths(1);
                from = Timestamp.valueOf(current.withDayOfMonth(1).atStartOfDay());
                till = Timestamp.valueOf(current.plusMonths(1).withDayOfMonth(1).atStartOfDay());
            }
            case THIS_YEAR, LAST_YEAR -> {
                LocalDate current = (period == THIS_YEAR) ? now : now.minusYears(1);
                from = Timestamp.valueOf(current.withDayOfYear(1).atStartOfDay());
                till = Timestamp.valueOf(current.plusYears(1).withDayOfYear(1).atStartOfDay());
            }
            default -> throw new PeriodUndefinedException("Invalid period.");
        }


        return reportDao.findProductsWithMostSales(from, till);
    }

    public List<Category> getCategoriesWithMostOrders(
    ) throws DataAccessException {
        return reportDao.findCategoriesWithMostOrders();
    }
}
