package com.rubinskyi.util.file;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class WarFileSearcher implements FileSearcher {
    private final ServletContext servletContext;

    @Override
    public File getFileByName(String filename) {
        return new File(servletContext.getRealPath("/WEB-INF/classes/" + filename));
    }
}
