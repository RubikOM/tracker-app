package com.rubinskyi.util.file;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public interface FileSearcher {
    String TESSIMAGE_FOLDER_NAME = "tessimage";

    File getFileByName(String filename);

    String getTessimageFolder();
}
