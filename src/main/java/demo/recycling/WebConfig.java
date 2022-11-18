package demo.recycling;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000");
        registry.addMapping("/**")
                .allowedOrigins("http://119.209.77.170:9926","http://119.209.77.170:3000","http://localhost:3000");
    }
}
