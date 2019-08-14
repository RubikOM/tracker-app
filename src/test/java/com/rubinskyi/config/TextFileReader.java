package com.rubinskyi.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Getter
@Component
public class TextFileReader {
    public String getContentByFileName(String fileName) {
        File fileByName = getFileByName(fileName);
        try {
            Stream<String> lines = Files.lines(fileByName.toPath(), StandardCharsets.UTF_8);
            return lines.collect(Collectors.joining(SPACE));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public File getFileByName(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
