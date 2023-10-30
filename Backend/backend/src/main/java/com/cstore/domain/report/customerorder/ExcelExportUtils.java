package com.cstore.domain.report.customerorder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;

public class ExcelExportUtils {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelExportUtils(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    private void createCells(
        Row row,
        int colCnt,
        Object value,
        CellStyle style
    ) {
        sheet.autoSizeColumn(colCnt);

        Cell cell = row.createCell(colCnt);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }

        cell.setCellStyle(style);
    }
}
