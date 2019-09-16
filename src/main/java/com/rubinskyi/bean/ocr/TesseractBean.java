package com.rubinskyi.bean.ocr;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@RequiredArgsConstructor
public class TesseractBean {
    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        return tesseract;
    }
}
