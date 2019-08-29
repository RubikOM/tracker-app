package com.rubinskyi.testBean;

import com.rubinskyi.bean.FileSearcherBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Slf4j
@Component
@RequiredArgsConstructor
public class TextFileReaderBean {
    private final FileSearcherBean fileSearcherBean;

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
        return fileSearcherBean.getFileByName(fileName);
    }
}
