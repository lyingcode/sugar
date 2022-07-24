package com.bitsoft.sugar.excel;

import com.bitsoft.sugar.model.Student;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Function;

public class ExcelReader {
    public static void main(String[] args) {
        Path excelFile = Paths.get("D:\\demo.xlsx");
        if (!Files.exists(excelFile)) {
            System.out.println("文件不存在" + excelFile.getFileName().toString());
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始文件读取");
        long freeMemory1 = Runtime.getRuntime().freeMemory();
        System.out.printf("写内容前剩余内存:%d", freeMemory1);
        System.out.println();
        try (InputStream inputStream = Files.newInputStream(excelFile, StandardOpenOption.READ)) {
            Workbook workbook = StreamingReader.builder().rowCacheSize(1000).bufferSize(8192).open(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 0;
            List<Student> studentList = Lists.newArrayList();
            for (Row row : sheet) {
                rowNum++;
                if (rowNum == 1) {
                    System.out.println("解析头文件");
                    parseHeader(row);
                }
                studentList.add(parseBody(row, ExcelReader::buildStudent));
                if (rowNum % 1000 == 0) {
                    batchProcess(studentList);
                    studentList.clear();
                }
            }
            if (studentList.size() > 0) {
                batchProcess(studentList);
                studentList.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long freeMemory2 = Runtime.getRuntime().freeMemory();
        System.out.printf("写内容后剩余内存:%d,占用内存:%d", freeMemory2, freeMemory1 - freeMemory2);
        System.out.println();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static void batchProcess(List<Student> studentList) {
//        System.out.println("批量处理数组信息");
//        if (studentList.size() > 0) {
//            studentList.forEach(student -> {
//                System.out.println(student);
//            });
//        }
//        System.out.println("批量处理数组信息完成");
    }

    private static Student parseBody(Row row, Function<Row, Student> converter) {
        return converter.apply(row);
    }

    private static Student buildStudent(Row row) {
        Student student = new Student();
        student.setId(row.getCell(0).getStringCellValue());
        student.setName(row.getCell(1).getStringCellValue());
        student.setSex(row.getCell(2).getStringCellValue());
        return student;
    }


    private static void parseHeader(Row row) {
        System.out.println("头信息处理:" + row);
    }
}
