package com.bitsoft.sugar.mvel;

import org.jeasy.rules.annotation.*;

@Rule(name = "都不能被整除")
public class OtherRule {
    @Condition
    public boolean isOther(@Fact("number") int number) {
        return number % 3 != 0 || number % 8 != 0;
    }

    @Action
    public void printSelf(@Fact("number") int number) {
        System.out.println("other number:" + number);
    }

    @Priority
    public int getPriority() {
        return 3;
    }

}
