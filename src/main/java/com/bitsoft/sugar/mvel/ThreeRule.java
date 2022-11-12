package com.bitsoft.sugar.mvel;

import org.jeasy.rules.annotation.*;

@Rule(name = "被3整除", description = "如果被3整除，则执行打印")
public class ThreeRule {
    @Condition
    public boolean isThree(@Fact("number") int num) {
        return num % 3 == 0;
    }

    @Action
    public void threeAction(@Fact("number") int number) {
        System.out.println("被3整除:" + number);
    }

    @Priority
    public int getPriority() {
        return 1;
    }
}
