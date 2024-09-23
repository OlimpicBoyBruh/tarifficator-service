package ru.neoflex.jd.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "")
@PropertySource("classpath:jwt.yaml")
public class JwtAppConfig {
    private String secret;
    private Integer lifeTime;
}
