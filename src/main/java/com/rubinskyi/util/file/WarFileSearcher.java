package com.rubinskyi.util.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Objects;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class WarFileSearcher implements FileSearcher {
    private final ServletContext servletContext;

    @Override
    public String getFilePathByName(String fileName) {
        log.info("I'm in File searcher ");
        String realPath = servletContext.getRealPath("/WEB-INF/classes/" + fileName);
        if (Objects.isNull(realPath)) {
            log.error("Can't find file by name: " + fileName);
            throw new IllegalArgumentException("file is not found!");
        }
        return realPath;
    }
}
