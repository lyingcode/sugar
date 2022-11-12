package com.bitsoft.sugar.mvel;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;

public class AnnotationRuleTest {
    @Test
    public void simpleRuleTest() {
        //只命中第一个
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);

        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        Rules rules = new Rules();
        rules.register(new EightRule());
        rules.register(new ThreeRule());
        rules.register(new ThreeEightRuleUnitGroup(new EightRule(), new ThreeRule()));
        rules.register(new OtherRule());
        Facts facts = new Facts();
        for (int i = 1; i <= 50; i++) {
            facts.put("number", i);
            rulesEngine.fire(rules, facts);
            System.out.println();
        }
    }
}
