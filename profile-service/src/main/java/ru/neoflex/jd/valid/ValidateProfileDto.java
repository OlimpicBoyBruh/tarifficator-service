package ru.neoflex.jd.valid;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.neoflex.jd.dto.ProfileDto;
import ru.neoflex.jd.dto.enumerated.Application;
import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateProfileDto {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(ProfileDto profileDto, Application applicationName) {
        log.info("Start validation process");
        Set<ConstraintViolation<ProfileDto>> violations = switch (applicationName) {
            case MOBILE -> {
                log.info("Invoke mobile validation");
                yield VALIDATOR.validate(profileDto, Validate.MobileGroup.class);
            }
            case GOSUSLUGI -> {
                log.info("Invoke gosuslugi validation");
                yield VALIDATOR.validate(profileDto, Validate.GosUslugiGroup.class);
            }
            case MAIL -> {
                log.info("Invoke mail validation");
                yield VALIDATOR.validate(profileDto, Validate.MailGroup.class);
            }
            case BANK -> {
                log.info("Invoke bank validation");
                yield VALIDATOR.validate(profileDto, Validate.BankGroup.class);
            }
        };

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<ProfileDto> violation : violations) {
                sb.append(violation.getMessage()).append(". ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
        log.info("Validation process finished");
    }
}
