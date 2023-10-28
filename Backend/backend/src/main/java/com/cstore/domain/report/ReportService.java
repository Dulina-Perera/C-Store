package com.cstore.domain.report;

import com.cstore.dao.order.OrderDao;
import com.cstore.dao.report.ReportDao;
import com.cstore.model.order.Order;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderDao orderDao;
    private final ReportDao reportDao;

    public String customerOrderReport(
        Long customerId,
        String reportFormat
    ) throws FileNotFoundException, JRException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String path = "/media/dulina/SP PHD U3/Education/Campus Documents/5. Semester 3/Database Systems/C Store" +
                "/Backend/backend/src/main/resources/static/reports/Customer-Order";

        List<Order> processedOrders = orderDao.findProcessedOrders(customerId);

        File generated;
        File file = ResourceUtils.getFile("classpath:static/reports/Customer-Order Report.jrxml");
        JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(processedOrders);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("created by", "C-Store");

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            generated = new File("%s/html/%d-%s.html".formatted(path, customerId, dateFormat.format(new Date())));
            JasperExportManager.exportReportToHtmlFile(
                jasperPrint,
                generated.getAbsolutePath());
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            generated = new File("%s/pdf/%d-%s.pdf".formatted(path, customerId, dateFormat.format(new Date())));
            JasperExportManager.exportReportToPdfFile(
                jasperPrint,
                generated.getAbsolutePath());
        }
        return "Report generated successfully.";
    }

    @Scheduled(cron = "00 00 00 01 1,4,7,10 *")
    public void quarterlySalesReport() {

    }
}
