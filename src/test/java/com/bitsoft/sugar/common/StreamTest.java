package com.bitsoft.sugar.common;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Item item2 = new Item("5", "1001", "king");
        List<Item> itemList = Lists.newLinkedList();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(new Item("3", "1001", "KKAM"));
        itemList.add(new Item("4", "1002", "KKJM"));
        itemList.add(new Item("5", "1002", "kkGo"));
        itemList.add(new Item("5", "1003", "kkGo"));
        Map<String, List<Item>> map = itemList.stream().collect(Collectors.groupingBy(Item::getId, Collectors.toList()));
        map.forEach((key, value) -> {
            System.out.printf("%s,%s", key, value);
        });

    }

    @Test
    public void streamMapTest() throws IOException {
        Path cmd = Paths.get("D:\\elk\\startELK.cmd");
        Stream<String> lines = Files.lines(cmd, StandardCharsets.UTF_8);
        Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
        words.filter(StringUtils::isNotBlank).forEach(System.out::println);
    }
}
