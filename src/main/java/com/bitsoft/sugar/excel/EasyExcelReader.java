package com.bitsoft.sugar.excel;

import com.alibaba.excel.EasyExcel;
import com.bitsoft.sugar.excel.listener.StudentDataListener;
import com.bitsoft.sugar.model.Student;
import org.springframework.util.StopWatch;

import java.util.List;

public class EasyExcelReader {
    public static void main(String[] args) {
        String fileName = "D:\\demo.xlsx";
        long freeMemory1 = Runtime.getRuntime().freeMemory();
        System.out.printf("写内容前剩余内存:%d", freeMemory1);
        System.out.println();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始文件解析");
        EasyExcel.read(fileName, Student.class, new StudentDataListener(EasyExcelReader::batchProcess)).sheet().doRead();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        long freeMemory2 = Runtime.getRuntime().freeMemory();
        System.out.printf("写内容后剩余内存:%d,占用内存:%d", freeMemory2, freeMemory1 - freeMemory2);
        System.out.println();
    }

    private static void batchProcess(List<Student> list) {

    }

}
