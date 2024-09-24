package ru.neoflex.jd.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.neoflex.jd.configuration.AppValidRulesProperties;
import ru.neoflex.jd.dto.ProfileDtoRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateProfileDto {
    private final AppValidRulesProperties appValidRulesProperties;

    public void validate(ProfileDtoRequest profileDtoRequest, String applicationName) {
        Map<String, List<String>> rules = appValidRulesProperties.getRulesValidate();

        List<String> applicationRules = Optional.ofNullable(rules.get(applicationName))
                .orElseThrow(() -> new IllegalArgumentException("No rules found for application: " + applicationName));

        applicationRules.forEach(value -> compareVariableName(value, profileDtoRequest));
    }

    private void compareVariableName(String variableName, ProfileDtoRequest profileDtoRequest) {
        try {
            Field field = profileDtoRequest.getClass().getDeclaredField(variableName);
            field.setAccessible(true);
            if (field.get(profileDtoRequest) == null) {
                throw new IllegalArgumentException("Validation failed: " + variableName);

            }
        } catch (NoSuchFieldException exception) {
            throw new IllegalArgumentException("Field not found: " + variableName);
        } catch (IllegalAccessException exception) {
            throw new IllegalArgumentException("Access denied for field: " + variableName);
        }
    }
}
