package com.rubinskyi.util.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

@Component
@Slf4j
@Profile("!prod")
public class LocalFileSearcher implements FileSearcher {
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
