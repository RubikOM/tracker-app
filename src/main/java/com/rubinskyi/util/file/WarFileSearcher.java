package com.rubinskyi.util.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class WarFileSearcher implements FileSearcher {
    private final ResourceLoader resourceLoader;
    private static final String WAR_RESOURCES_FOLDER = "classpath:WEB-INF/classes/";
    @Value("classpath:tessimage")
    private File IMAGE_FOLDER_NAME;

    @Override
    public File getFileByName(String filename) {
        Resource resource = resourceLoader.getResource(WAR_RESOURCES_FOLDER + filename);
        return new File(getFilenameFromResource(resource));
    }

    @Override
    public String getTessimageFolder() {
        Resource resource = resourceLoader.getResource(WAR_RESOURCES_FOLDER + TESSIMAGE_FOLDER_NAME);
        String tessimageFolder = getFilenameFromResource(resource);
        String absolutePath = new File(tessimageFolder).getAbsolutePath();
        return cleanAbsolutePath(absolutePath);
    }

    private String getFilenameFromResource(Resource resource) {
        String path;
        try {
            path = resource.getURL().getPath();
        } catch (IOException e) {
            log.error("Cannot find file by name: " + resource);
            throw new RuntimeException(e);
        }
        return path;
    }

    private String cleanAbsolutePath(String absolutePath) {
        String corruptedPartEnding = "file:\\";
        String cleanedPath = absolutePath.replace("!", EMPTY);
        int i = cleanedPath.indexOf(corruptedPartEnding);
        return cleanedPath.substring(i + corruptedPartEnding.length());
    }
}
