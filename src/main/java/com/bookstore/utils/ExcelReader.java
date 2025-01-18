package com.bookstore.utils;

import com.bookstore.factory.Factory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.LinkedList;
import java.util.List;

public class ExcelReader {

    static XSSFWorkbook xssfWorkbook;
    static XSSFSheet xssfSheet;
    static XSSFRow xssfRow;
    static XSSFCell xssfCell;

    static String path = System.getProperty("user.dir");

    private ExcelReader() {
        setExcelName(path + "/Data/" + Factory.getPropValues("excel"));
        setExcelSheet(Factory.getPropValues("excelSheet"));
        System.out.println("Working with excel: " + getExcelName() + " & sheet: " + getExcelSheet());
        try {
            xssfWorkbook = new XSSFWorkbook(getExcelName());
        } catch (Exception ex) {
            System.out.println("Caught exception while working with excel with message: " + ex.getMessage());
        }
        xssfSheet = xssfWorkbook.getSheet(getExcelSheet());
    }

    public static String getExcelName() {
        return excelName;
    }

    public static void setExcelName(String excelName) {
        ExcelReader.excelName = excelName;
    }

    static String excelName;

    public static String getExcelSheet() {
        return excelSheet;
    }

    public static void setExcelSheet(String excelSheet) {
        ExcelReader.excelSheet = excelSheet;
    }

    static String excelSheet;
    static int rowCount, colCount;

    public static int getRowCount() {
        rowCount = xssfSheet.getLastRowNum();
        return rowCount;
    }

    public static int getColCount() {
        colCount = xssfSheet.getRow(0).getLastCellNum();
        return colCount;
    }

    public static List<Object> getEntireExcelData() {
        List<Object> cellData = new LinkedList<>();
        for (int i = 1; i <= getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                xssfRow = xssfSheet.getRow(i);
                xssfCell = xssfRow.getCell(j);

                CellType cellType = xssfCell.getCellType();
                switch (cellType) {
                    case STRING -> {
                        cellData.add(xssfCell.getStringCellValue());
                        break;
                    }
                    case NUMERIC -> {
                        cellData.add(xssfCell.getNumericCellValue());
                        break;
                    }
                    case BOOLEAN -> {
                        cellData.add(xssfCell.getBooleanCellValue());
                        break;
                    }
                    case FORMULA -> {
                        cellData.add(xssfCell.getCellFormula());
                        break;
                    }
                    default -> {
                        System.out.println("unable to read value");
                        break;
                    }
                }
            }
        }
        return cellData;
    }

    public static List<Object> getColumnData(String columnName) {
        List<Object> cellData = new LinkedList<>();
        for (int i = 1; i <= getRowCount(); i++) {
            xssfRow = xssfSheet.getRow(i);
            for (int j = 0; j < getColCount(); j++) {
                xssfCell = xssfRow.getCell(j);
                if (xssfSheet.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase(columnName)) {

                    CellType cellType = xssfCell.getCellType();
                    switch (cellType) {
                        case STRING -> {
                            cellData.add(xssfCell.getStringCellValue());
                            break;
                        }
                        case NUMERIC -> {
                            cellData.add(xssfCell.getNumericCellValue());
                            break;
                        }
                        case BOOLEAN -> {
                            cellData.add(xssfCell.getBooleanCellValue());
                            break;
                        }
                        case FORMULA -> {
                            cellData.add(xssfCell.getCellFormula());
                            break;
                        }
                        default -> {
                            System.out.println("unable to read value");
                            break;
                        }
                    }
                }
            }
        }
        return cellData;
    }

    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        System.out.println(getEntireExcelData());
        System.out.println(getColumnData("UserName"));
    }
}
