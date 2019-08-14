package com.rubinskyi.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Slf4j
@Getter
@Component
public class TextFileReader {
    public String getContentByFileName(String fileName) {
        File fileByName = getFileByName(fileName);
        try {
            Stream<String> lines = Files.lines(fileByName.toPath(), StandardCharsets.UTF_8);
            return lines.collect(Collectors.joining(SPACE));
        } catch (IOException e) {
            log.error("Error while working with file ", e);
            throw new RuntimeException(e);
        }
    }

    public File getFileByName(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            log.error("Cannot open file with name " + fileName);
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
