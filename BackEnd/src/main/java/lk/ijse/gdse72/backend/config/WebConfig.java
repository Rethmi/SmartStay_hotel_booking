package lk.ijse.gdse72.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expose the uploads directory
        exposeDirectory("uploads", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // For Windows, we need to add a leading slash
        if (uploadPath.startsWith("C:") || uploadPath.startsWith("D:")) {
            uploadPath = "/" + uploadPath;
        }

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}