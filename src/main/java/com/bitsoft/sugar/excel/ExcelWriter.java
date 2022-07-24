package com.bitsoft.sugar.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelWriter {
    public static void main(String[] args) {
        Path file = Paths.get("D:\\demo.xlsx");
        if (!Files.exists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (Workbook workbook = new SXSSFWorkbook(); OutputStream outputStream = Files.newOutputStream(file)) {
            Sheet sheet = workbook.createSheet();
            //write head
            Row row = sheet.createRow(0);
            Cell cell;
            String[] title = {"id", "name", "sex"};
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("写头");
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
            }
            stopWatch.stop();
            stopWatch.start("写内容");
            long freeMemory1 = Runtime.getRuntime().freeMemory();
            System.out.printf("写内容前剩余内存:%d", freeMemory1);
            for (int i = 1; i < 1000000; i++) {
                Row contentRow = sheet.createRow(i);
                Cell cCell = contentRow.createCell(0);
                cCell.setCellValue("a" + i);
                cCell = contentRow.createCell(1);
                cCell.setCellValue("user" + i);
                cCell = contentRow.createCell(2);
                cCell.setCellValue("男");
            }
            long freeMemory2 = Runtime.getRuntime().freeMemory();
            System.out.printf("写内容后剩余内存:%d,占用内存:%d", freeMemory2, freeMemory1 - freeMemory2);
            System.out.println();
            stopWatch.stop();
            stopWatch.start("创建文件");

            stopWatch.stop();
            stopWatch.start("写入文件");

            workbook.write(outputStream);

            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
