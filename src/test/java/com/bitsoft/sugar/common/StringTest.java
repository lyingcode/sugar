package com.bitsoft.sugar.common;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringTest {
    @Test
    public void stringTest() {
        String value = Strings.emptyToNull("");
        assertNull(value);

        value = Strings.nullToEmpty(value);
        assertEquals("", value);

        assertTrue(Strings.isNullOrEmpty(""));
        assertTrue(Strings.isNullOrEmpty(null));

        assertEquals("000001", Strings.padStart("1", 6, '0'));
        assertEquals("100001", Strings.padStart("100001", 6, '0'));

        assertEquals("10000", Strings.padEnd("1", 5, '0'));
        assertEquals("10010", Strings.padEnd("1001", 5, '0'));
        assertEquals("20.00", Strings.padEnd("20.", 5, '0'));
        assertEquals("aaaaa", Strings.repeat("a", 5));

        assertEquals("h", Strings.commonPrefix("hello", "hi"));
        assertEquals("ja", Strings.commonPrefix("jameszhang", "jaledi"));

        assertEquals("am", Strings.commonSuffix("gam", "kimam"));
        assertEquals("ame", Strings.commonSuffix("game", "kame"));

        assertEquals("name:zhangsan,age:20", Strings.lenientFormat("name:%s,age:%s", "zhangsan", "20"));


    }
}
