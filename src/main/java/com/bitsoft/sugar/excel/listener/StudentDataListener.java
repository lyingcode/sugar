package com.bitsoft.sugar.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.bitsoft.sugar.model.Student;

import java.util.List;
import java.util.function.Consumer;

public class StudentDataListener extends PageReadListener<Student> {
    private Integer totalNum = 0;

    public StudentDataListener(Consumer<List<Student>> consumer) {
        super(consumer);
    }

    @Override
    public void invoke(Student data, AnalysisContext context) {
        super.invoke(data, context);
        totalNum++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        super.doAfterAllAnalysed(context);
        System.out.println("共处理记录" + totalNum);
    }
}
