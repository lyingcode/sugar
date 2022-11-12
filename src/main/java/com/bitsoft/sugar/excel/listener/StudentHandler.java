package com.bitsoft.sugar.excel.listener;

import com.bitsoft.sugar.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.List;

@Slf4j
public class StudentHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
    private final List<Student> list = Lists.newArrayList();
    private Student student;
    private Integer totalRow;

    /**
     * 每一行的第一列回调
     *
     * @param rowNum 行号
     */
    @Override
    public void startRow(int rowNum) {
        if (rowNum > 0) {
            student = new Student();
        }
    }

    /**
     * 每一行最后一列回调
     *
     * @param rowNum 行号
     */
    @Override
    public void endRow(int rowNum) {
        list.add(student);
        if (list.size() % 1000 == 0) {
            batchProcess(list);
            list.clear();
        }
        totalRow = rowNum;
    }

    private void batchProcess(List<Student> list) {
        if (log.isDebugEnabled()) {
            log.debug("size:{}", list.size());
        }
    }

    /**
     * 每一个单元格回调
     *
     * @param cellReference  head
     * @param formattedValue 列值
     * @param comment        备注信息
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (student != null) {
            String pref = cellReference.substring(0, 1);
            switch (pref) {
                case "A":
                    student.setId(formattedValue);
                    break;
                case "B":
                    student.setName(formattedValue);
                    break;
                case "C":
                    student.setSex(formattedValue);
                    break;
                default:
                    log.info("{}", pref);
            }
        }
    }

    /**
     * 最后一行回调
     */
    @Override
    public void endSheet() {
        if (!list.isEmpty()) {
            batchProcess(list);
            list.clear();
        }
        if (log.isDebugEnabled()) {
            log.debug("totalRow:{}", totalRow);
        }
    }
}
