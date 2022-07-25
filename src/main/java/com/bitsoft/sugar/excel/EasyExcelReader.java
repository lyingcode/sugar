package com.bitsoft.sugar.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.bitsoft.sugar.excel.listener.StudentDataListener;
import com.bitsoft.sugar.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class EasyExcelReader {
    public static void main(String[] args) {
        String userHome = System.getProperty("user.home");
        String fileName = Paths.get(userHome, "demo.xlsx").toString();
        long freeMemory1 = Runtime.getRuntime().freeMemory();
        log.info("写内容前剩余内存:{}", freeMemory1);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始文件解析");
        EasyExcelFactory.read(fileName, Student.class, new StudentDataListener(EasyExcelReader::batchProcess)).sheet().doRead();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        long freeMemory2 = Runtime.getRuntime().freeMemory();
        log.info("写内容后剩余内存:{}},占用内存:{}", freeMemory2, freeMemory1 - freeMemory2);
    }

    private static void batchProcess(List<Student> list) {
        // do nothing
    }

}
