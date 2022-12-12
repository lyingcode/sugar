package com.bitsoft.sugar.common;

import com.bitsoft.sugar.UnitTestContextBase;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest extends UnitTestContextBase {
    @Test
    public void mapTest() {
        Map<String, String> map = Maps.newHashMap();
        for (int i = 0; i < 100; i++) {
            map.put("name" + i, String.valueOf(i));
        }
        assertEquals(100, map.size());
        assertEquals(false, map.isEmpty());
        assertEquals(Boolean.FALSE, map.isEmpty());
        assertEquals(false, Boolean.FALSE);
        assertTrue(map.containsKey("name1"));
        assertFalse(map.containsKey("name101"));
        assertTrue(map.containsValue("1"));
        assertFalse(map.containsValue("name1"));
        assertEquals("1", map.get("name1"));
        assertEquals(null, map.get("name101"));
        assertEquals("", map.getOrDefault("name101", ""));
        assertEquals("99", map.getOrDefault("name99", ""));
        map.put("name101", "101");
        assertEquals("101", map.getOrDefault("name101", ""));
        map.remove("name101");
        assertNull(map.get("name101"));
        map.remove("name101");
        assertNull(map.get("name101"));

        map.entrySet().removeIf(entry -> "name2".equals(entry.getKey()));
        assertNull(map.get("name2"));
    }
}
