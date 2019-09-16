package com.rubinskyi.bean.ocr;

import com.rubinskyi.util.file.LocalFileSearcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class ResourceLoaderBean {

    @Bean
    @Profile("prod")
    ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }

    @Bean
    @Profile("!prod")
    @Qualifier("localFileSearcher")
    ClassLoader localFileSearcherClassLoader() {
        return LocalFileSearcher.class.getClassLoader();
    }
}
