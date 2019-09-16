package com.rubinskyi.util.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

@Component
@Slf4j
@Profile("!prod")
@RequiredArgsConstructor
public class LocalFileSearcher implements FileSearcher {
    @Qualifier("localFileSearcher")
    private final ClassLoader classLoader;

    public File getFileByName(String fileName) {
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            log.error("Cannot open file with name " + fileName);
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
