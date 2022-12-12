package com.bitsoft.sugar.common;

import org.apache.catalina.webresources.FileResource;
import org.apache.xmlbeans.impl.xb.xsdschema.OpenAttrs;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesTest {
    private static char[] words = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    @Test
    public void copyTo() throws IOException {
        Path localText = Paths.get("D:\\local");
        if (Files.exists(localText)) {
            System.out.println("文件已经存在" + localText.toString());
        }
        URI uri = URI.create("https://www.runoob.com/java/file-create-temp.html");
        Path tmpFile = Files.createTempFile(UUID.randomUUID().toString(), ".txt");
        System.out.println(tmpFile.toString());
        try (InputStream inputStream = uri.toURL().openStream()) {
            Files.copy(inputStream, tmpFile, StandardCopyOption.REPLACE_EXISTING);
        }
        Stream<String> lines = Files.lines(tmpFile, StandardCharsets.UTF_8);
        Stream<String> words = lines.flatMap(line -> Stream.of(line.split("[\\[/><!--;\\]=\\|:\\.]")));
        Map<String, Long> map = words.filter(StringUtils::isNotBlank).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        map.forEach((key, value) -> {
            System.out.println("key" + value);
        });
    }

    @Test
    public void writeTest() throws IOException {
        Path outFile = Paths.get("D:\\local", "demo.out");
        if (Files.notExists(outFile)) {
            Files.createFile(outFile);
        }
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outFile, StandardCharsets.UTF_8, StandardOpenOption.WRITE)) {
            while (Files.size(outFile) < 1024 * 1024 * 1024) {
                String randomStr = genRandomStr();
                System.out.println(randomStr);
                bufferedWriter.write(randomStr);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readTest() throws IOException {
        Path inFile = Paths.get("D:\\local", "demo.out");
        if (Files.notExists(inFile)) {
            System.out.println("文件不存在");
        }
        InputStreamReader is = new FileReader(inFile.toFile());
        char[] buff = new char[4096];
        long counts = 0;
        int offset = 0;
        while ((offset = is.read(buff)) != -1) {
            counts = counts + offset;
            System.out.println(new String(buff));
        }
        System.out.println(counts);
        is.close();
    }

    @Test
    public void readTest2() throws FileNotFoundException {
        Path inFile = Paths.get("D:\\local", "demo.out");
        if (Files.notExists(inFile)) {
            System.out.println("文件不存在");
        }
        try (FileInputStream fileInputStream = new FileInputStream(inFile.toFile())) {
            byte[] buff = new byte[4096];
            int counts = 0;
            int offset = 0;
            while ((offset = fileInputStream.read(buff)) != -1) {
                counts += offset;
                System.out.println(new String(buff, StandardCharsets.UTF_8));
            }
            System.out.println("总共读取字节:" + counts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String genRandomStr() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            int num = new Random().nextInt(words.length);
            str.append(words[num]);
        }
        return str.toString();
    }
}
