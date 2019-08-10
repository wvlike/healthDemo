package com.ismyself.excel;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * package com.ismyself.excel;
 *
 * @auther txw
 * @create 2019-07-31  16:50
 * @description：
 */
public class ExcelDemo {

    public void fun01() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\aboutmyself2\\healthProject\\health_parent\\health_web\\src\\main\\webapp\\template\\create.xlsx");
        XSSFSheet sheetAt = workbook.getSheetAt(1);
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
            System.out.println("----------------------");
        }
        workbook.close();
    }

    public void fun02() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\aboutmyself2\\healthProject\\health_parent\\health_web\\src\\main\\webapp\\template\\create.xlsx");

        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
            System.out.println("-------------");
        }

        workbook.close();

    }

    public void fun03() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = workbook.createSheet("学生名单");
        XSSFRow row01 = xssfSheet.createRow(0);
        row01.createCell(0).setCellValue("姓名");
        row01.createCell(1).setCellValue("性别");
        row01.createCell(2).setCellValue("地址");

        XSSFRow row02 = xssfSheet.createRow(1);
        row02.createCell(0).setCellValue("小明");
        row02.createCell(1).setCellValue("男");
        row02.createCell(2).setCellValue("地图");

        try {
            FileOutputStream fos = new FileOutputStream("E:\\aboutmyself2\\healthProject\\health_parent\\health_web\\src\\main\\webapp\\template\\student.xlsx");
            workbook.write(fos);
            fos.flush();
            fos.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fun04() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\aboutmyself2\\healthProject\\health_parent\\health_web\\src\\main\\webapp\\template\\create.xlsx");
        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.println(cell.getStringCellValue());
                }
            }
            System.out.println("-------------------");
        }
    }

    public void fun05() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = workbook.createSheet("到底想该干嘛");
        XSSFRow titleRow = xssfSheet.createRow(0);
        titleRow.createCell(0).setCellValue("姓名");
        titleRow.createCell(1).setCellValue("时间");
        titleRow.createCell(2).setCellValue("地点");
        for (int i = 1; i <= 10; i++) {
            XSSFRow row = xssfSheet.createRow(i);
            row.createCell(0).setCellValue("儿子"+i);
            row.createCell(1).setCellValue("你挑时间"+new Date());
            row.createCell(2).setCellValue("你学地点"+ UUID.randomUUID().toString());
        }
        FileOutputStream fos = new FileOutputStream("E:\\aboutmyself2\\healthProject\\health_parent\\health_web\\src\\main\\webapp\\template\\dajia.xlsx");
        workbook.write(fos);
        fos.flush();
        fos.close();
        workbook.close();
    }
    public void fun06() throws Exception {
        FileInputStream fis = new FileInputStream("E:\\aboutmyself2\\healthProject\\health_parent\\health_web\\src\\main\\webapp\\template\\dajia.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet fristSheet = workbook.getSheetAt(0);
        int lastRowNum = fristSheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = fristSheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell .getStringCellValue());
            }
            System.out.println("---------------------------------");
        }
    }
}
