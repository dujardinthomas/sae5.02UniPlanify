package fr.sae502.uniplanify;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Taille maximale autorisée pour un fichier uploadé (10MB dans cet exemple)
        DataSize maxFileSize = DataSize.ofMegabytes(10);
        factory.setMaxFileSize(maxFileSize);
        // Taille maximale totale de la requête (10MB dans cet exemple)
        DataSize maxRequestSize = DataSize.ofMegabytes(10);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
}
