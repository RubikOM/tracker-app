package com.rubinskyi.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Getter
@Component
public class TextFileReader {
    private static final String FILE_PREFIX = "src/test/resources/ocrText/";

    public String getContentByFileName(String fileName) {
        try {
            Stream<String> lines = Files.lines(Paths.get(FILE_PREFIX.concat(fileName)), StandardCharsets.UTF_8);
            return lines.collect(Collectors.joining(SPACE));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
