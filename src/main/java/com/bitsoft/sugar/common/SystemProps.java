package com.bitsoft.sugar.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Properties;

@Slf4j
public class SystemProps {
    public static void main(String[] args) {
        Properties properties =  System.getProperties();
        for(Map.Entry<Object, Object> entry:properties.entrySet()){
            log.info("key:{},value:{}",entry.getKey(),entry.getValue());
        }
    }
}
