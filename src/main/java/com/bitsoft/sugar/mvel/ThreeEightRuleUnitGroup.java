package com.bitsoft.sugar.mvel;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.support.composite.UnitRuleGroup;

@Rule(name = "同时被3和8整除")
public class ThreeEightRuleUnitGroup extends UnitRuleGroup {
    public ThreeEightRuleUnitGroup(Object... rules) {
        for (Object rule : rules) {
            addRule(rule);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
