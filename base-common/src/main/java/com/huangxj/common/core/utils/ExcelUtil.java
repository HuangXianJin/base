package com.huangxj.common.core.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static List<Map<String, Object>> getExcelInfo(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        // Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        Integer totalCells = null;
        // Excel的列数
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> recordMap = null;
        // 循环Excel行数,从第二行开始
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row==null||isRowEmpty(row)) {
                continue;
            }
            recordMap = new HashMap<String, Object>();

            // 循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                if (cell != null) {
                    recordMap.put(getCellValue(sheet.getRow(0).getCell(c)), getCellValue(cell));
                }
            }
            dataList.add(recordMap);
        }

        return dataList;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");

        if(cell == null){
            return "";
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = df.format(cell.getNumericCellValue()).toString();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 批量上传数据方法
     *
     * @param file
     * @return
     */
    public static List analysisExcel(MultipartFile file) {

        String names = file.getOriginalFilename();
        String xlsx = ".xlsx";
        if (!names.endsWith(xlsx)) {
            return null;
        }
        InputStream inputStream = null;
        List<Map<String, Object>> list = null;
        try {
            inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            list = ExcelUtil.getExcelInfo(workbook);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }


}
