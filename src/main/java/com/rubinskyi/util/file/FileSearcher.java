package com.rubinskyi.util.file;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.slf4j.LoggerFactory.getLogger;

public interface FileSearcher {
    File getFileByName(String fileName);

    default File getFileFromMultipart(String filename, MultipartFile multipartFile) {
        File file = new File(filename);
        try {
            file.createNewFile();
            try (InputStream is = multipartFile.getInputStream()) {
                Files.copy(is, file.toPath(), REPLACE_EXISTING);
            }
        } catch (IOException e) {
            LogHolder.LOGGER.error("Error while creating new file for name: " + filename);
        }
        return file;
    }
    final class LogHolder {
        static final Logger LOGGER = getLogger(FileSearcher.class);
    }
}
