package com.rubinskyi.util.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import java.io.File;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class WarFileSearcher implements FileSearcher {
    private final ResourceLoader resourceLoader;
    private static final String WAR_RESOURCES_FOLDER = "classpath:WEB-INF/classes/";

    @Override
    public File getFileByName(String filename) {
        Resource resource = resourceLoader.getResource(WAR_RESOURCES_FOLDER + filename);
        return new File(((ServletContextResource) resource).getPathWithinContext());
    }
}
