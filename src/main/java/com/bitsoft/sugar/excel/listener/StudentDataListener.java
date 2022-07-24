package com.bitsoft.sugar.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.bitsoft.sugar.model.Student;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;

public class StudentDataListener implements ReadListener<Student> {
    private List<Student> list = Lists.newArrayList();
    private Long totalRow = 0L;
    private Integer batchProcessNum = 0;

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        ReadListener.super.onException(exception, context);
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        for (Map.Entry<Integer, ReadCellData<?>> entry : headMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        Object obj = context.getCustom();
        System.out.println(obj);
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        System.out.println("extra:" + extra);
        System.out.println("extra:" + context);
    }

    @Override
    public void invoke(Student o, AnalysisContext analysisContext) {
        totalRow++;
        list.add(o);
        if (list.size() % 1000 == 0) {
            batchProcess(list);
            list.clear();
        }
    }

    private void batchProcess(List<Student> list) {
        batchProcessNum++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size() > 0) {
            batchProcess(list);
        }
        System.out.println("处理完了" + totalRow + "条记录");
    }
}
