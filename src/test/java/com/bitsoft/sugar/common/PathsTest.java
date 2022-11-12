package com.bitsoft.sugar.common;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.function.BiConsumer;

public class PathsTest {
    private static BiConsumer<String, Object> pt = (t, r) -> {
        System.out.println(String.format("%s:%s", t, r));
    };

    @SneakyThrows
    @Test
    public void pathTest() {
        String rootPath = "D:\\projects\\sugar\\..\\sugar\\src\\main";
        Path path = Paths.get(rootPath);
        System.out.println(path);
        Assertions.assertEquals(true, Files.exists(path));
        pt.accept("file absolute path", path.toAbsolutePath());
        pt.accept("file root", path.getRoot());
        pt.accept("file normalize", path.normalize());
        pt.accept("file parent", path.getParent());
        pt.accept("file subPath", path.subpath(0, 3));
        pt.accept("relative path", path.relativize(Paths.get("D:\\projects\\sugar")));
        pt.accept("file url", path.toUri());
        pt.accept("file normalize url", path.normalize().toUri());
        pt.accept("file normalize relative path", path.normalize().relativize(Paths.get("D:\\projects\\sugar")));
        pt.accept("file fileName", path.getFileName());
        pt.accept("fileName count", path.normalize().getNameCount());
        for (int i = 0; i < path.normalize().getNameCount(); i++) {
            pt.accept(String.valueOf(i), path.normalize().getName(i));
        }
        Path pptxFile = Paths.get("D:\\架构巡检\\移动展业系统架构巡检.pptx");
        pt.accept("file size", Files.size(pptxFile) / 1024);
        pt.accept("file owner", Files.getOwner(pptxFile));
        pt.accept("file contentType", Files.probeContentType(pptxFile));
        pt.accept("file store", Files.getFileStore(pptxFile));
        pt.accept("file lastModify", Files.getLastModifiedTime(pptxFile));
        BasicFileAttributes attributes = Files.readAttributes(pptxFile, BasicFileAttributes.class);
        pt.accept("file creationTime", attributes.creationTime());
        pt.accept("file lastModifiedTime", attributes.lastModifiedTime());
        pt.accept("file lastAccessTime", attributes.lastAccessTime());
        pt.accept("file isRegularFile", attributes.isRegularFile());

        pt.accept("file isDirectory", attributes.isDirectory());
        pt.accept("file isSymbolicLink", attributes.isSymbolicLink());
        pt.accept("file isOther", attributes.isOther());
        pt.accept("file size", attributes.size());
        pt.accept("file fileKey", attributes.fileKey());

        pt.accept("files is file", Files.isRegularFile(pptxFile));
        pt.accept("files isReadable", Files.isReadable(pptxFile));
        pt.accept("files isWritable", Files.isWritable(pptxFile));
        pt.accept("files isExecutable", Files.isExecutable(pptxFile));

        Path copyFile = Paths.get("D:\\架构巡检\\newCopy.pptx");
        Files.deleteIfExists(copyFile);
        pt.accept("复制完成", Files.copy(pptxFile, copyFile));

        Path moveFile = Paths.get("D:\\架构巡检\\moveFile.pptx");
        Files.deleteIfExists(moveFile);
        pt.accept("move完成", Files.move(copyFile, moveFile));
        Path txtFile = Paths.get("D:\\架构巡检\\demo.txt");
        if(Files.notExists(txtFile)){
            Files.createFile(txtFile);
        }
        Files.write(txtFile, "w1".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        Files.write(txtFile, "中文".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        Files.write(txtFile, "最新内容".getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }
}
