package com.bitsoft.sugar.mvel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.ehcache.impl.internal.store.loaderwriter.LocalLoaderWriterStore;
import org.junit.jupiter.api.Test;
import org.mvel2.MVEL;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

public class MvelTest {
    @Test
    public void empty() {
        String express = "a == empty && b== empty";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", "");
        paramMap.put("b", null);
        Object result = MVEL.eval(express, paramMap);
        System.out.println(result);
    }

    @Test
    public void nullTest() {
        String express = "a == null && b == null";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", null);
        paramMap.put("b", null);
        Object result = MVEL.eval(express, paramMap);
        System.out.println(result);
    }

    @Test
    public void transfer() {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", 123);
        paramMap.put("b", "123");
        Object result = MVEL.eval("a==b", paramMap);
        System.out.println(result);
    }

    @Test
    public void fruit() {
        Fruit fruit = new Fruit();
        fruit.setName("苹果");
        fruit.setAge(1000);
        fruit.setAddr(new Address("深圳", "广东"));
        Map<String, Object> param = Maps.newHashMap();
        List<Fruit> fruitList = List.of(fruit);
        param.put("fruit", fruit);
        param.put("fruits", fruitList);
        Object result = MVEL.eval("fruit.name", param);
        System.out.println(result);
        List<String> list = ImmutableList.of("a", "b", "c");
        param.put("simple", list);
        result = MVEL.eval("simple.contains('c')", param);
        System.out.println(result);
        param.put("user", null);
        System.out.println(MVEL.eval(" fruit.?name", param));
        System.out.println(MVEL.eval("fruit.name[0]", param));
        param.put("productType", "1");
        System.out.println(MVEL.eval("if (productType == 1){return '100001'}else if(productType== 2){return '100002';}else{return 'other';}", param));
        MVEL.eval("str='abcdefg';System.out.println(fruit.name);", param);
        MVEL.eval("list=['james','jump'];foreach(str:list){System.out.println(str)};", param);
        MVEL.eval("int i=1; while(i<=10){System.out.print(i);i++};", param);
        MVEL.eval("def hello(str){System.out.println(str)}; hello('jameszhang');", param);
    }

    @Test
    public void formula() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("A", 100);
        param.put("B", 200);
        param.put("C", new BigDecimal("1.1"));

        Object result = MVEL.eval("result=A*B*C", param);
        BigDecimal rs = new BigDecimal(String.valueOf(result));
        System.out.println(rs);
    }

    @Test
    public void timeCompare() {
        Map<String, Object> param = Maps.newHashMap();
        LocalDateTime t1 = LocalDateTime.now();
        LocalDateTime t2 = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        param.put("day1",t1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        param.put("day2", t2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Object result = MVEL.eval("result = day2-day1",param);
        System.out.println(result);
    }

    @Data
    class Fruit {
        String name;
        Integer age;
        Address addr;
    }

    @Data
    @AllArgsConstructor
    class Address {
        String city;
        String prov;
    }
}
