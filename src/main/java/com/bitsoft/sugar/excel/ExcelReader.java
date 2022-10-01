package com.bitsoft.sugar.excel;

import com.bitsoft.sugar.model.Student;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class ExcelReader {
    public static void main(String[] args) {
        Path excelFile = Paths.get("/Users/jameszhang/demo.xlsx");
        if (!Files.exists(excelFile)) {
            log.info("文件不存在{}", excelFile.getFileName().toString());
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始文件读取");
        long freeMemory1 = Runtime.getRuntime().freeMemory();
        long maxFree1 = Runtime.getRuntime().maxMemory();
        log.info("写内容前剩余内存:{},最大内存:{}", freeMemory1, maxFree1);
        try (InputStream inputStream = Files.newInputStream(excelFile, StandardOpenOption.READ);
             Workbook workbook = StreamingReader.builder().rowCacheSize(1000).bufferSize(1024 * 10).open(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 0;
            List<Student> studentList = Lists.newArrayList();
            for (Row row : sheet) {
                rowNum++;
                if (rowNum == 1) {
                    log.info("解析头文件");
                    parseHeader(row);
                }
                studentList.add(parseBody(row, ExcelReader::buildStudent));
                if (rowNum % 1000 == 0) {
                    batchProcess(studentList);
                    studentList.clear();
                }
            }
            if (!studentList.isEmpty()) {
                batchProcess(studentList);
                studentList.clear();
            }
        } catch (IOException e) {
            log.error("文件解析异常",e);
        }
        long freeMemory2 = Runtime.getRuntime().freeMemory();
        long maxFree2 = Runtime.getRuntime().maxMemory();
        log.info("写内容后剩余内存:{},占用内存:{}},最大内存:{}}", freeMemory2, freeMemory1 - freeMemory2, maxFree2);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    private static void batchProcess(List<Student> studentList) {
        //do nothing here
        if(log.isDebugEnabled()){
            log.debug("size:{}",studentList.size());
        }
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
        log.info("头信息处理:{}" , row);
    }
}
