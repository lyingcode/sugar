package com.bitsoft.sugar.cache;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCache {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .recordStats()
                .removalListener(notification -> System.out.println(notification.getKey() + ":" + notification.getCause()))
                .build(new CacheLoader() {
                    @Override
                    public Object load(Object key) throws Exception {
                        return Thread.currentThread().getName() + ":" + key;
                    }
                });
        initCache(cache);
        System.out.println("cache.size():" + cache.size());
        displayCache(cache);
        Thread.sleep(1000);
        System.out.println(Strings.repeat("=",20));
        System.out.println("是否存在:" + cache.getIfPresent("17"));
        Thread.sleep(2500);
        System.out.println(Strings.repeat("=",20));
        System.out.println("休息后是否存在:" + cache.getIfPresent("17"));
        displayCache(cache);
    }

    public static void initCache(LoadingCache<String, Object> cache) throws ExecutionException {
        for (int i = 0; i < 20; i++) {
            cache.get(String.valueOf(i));
        }
    }

    public static void displayCache(LoadingCache cache) {
        Iterator its = cache.asMap().entrySet().iterator();
        while (its.hasNext()) {
            System.out.println(its.next().toString());
        }
    }
}
