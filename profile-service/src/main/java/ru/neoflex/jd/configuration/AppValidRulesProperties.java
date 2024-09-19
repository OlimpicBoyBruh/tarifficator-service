package ru.neoflex.jd.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.Map;

@Component
@Data
@Validated
@ConfigurationProperties(prefix = "x-source")
public class AppValidRulesProperties {
    @NotNull
    private Map<String, List<String>> rulesValidate;
}