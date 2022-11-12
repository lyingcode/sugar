package com.bitsoft.sugar.common;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Item {
        private String id;
        private String refNo;
        private String name;
    }

    @Test
    public void streamTest() {
        Item item1 = new Item("1", "1001", "jameszhang");
        Item item2 = new Item("2", "1001", "king");
        List<Item> itemList = Lists.newLinkedList();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(new Item("3", "1001", "KKAM"));
        itemList.add(new Item("4", "1002", "KKJM"));
        itemList.add(new Item("5", "1002", "kkGo"));
        Map<String, List<Item>> result = itemList.stream().collect(Collectors.groupingBy(Item::getRefNo));
//        List<String> flatList = result.get("1001").stream().flatMap(Collection::stream).collect(Collectors.toList());
        Map<String, Long> rs1 = itemList.stream().collect(Collectors.groupingBy(Item::getRefNo, Collectors.counting()));
        System.out.println(rs1.getOrDefault("1001", 0L));
    }
}
