package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.jd.dto.ProfileDto;
import ru.neoflex.jd.service.ProfileRepositoryService;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Profile service", description = "Сервис для создания/поиска аккаунта.")
public class ProfileController {

    private final ProfileRepositoryService profileRepositoryService;

    @Operation(summary = "Создание аккаунта.",
            description = "Поступает информация  для создания аккаунта, происходит валидации и сохранение в БД."
    )
    @PostMapping("/create")
    public void createProfile(@RequestHeader("x-Source") @Parameter(description = "Источник",
            example = "mobile") String application, @RequestBody ProfileDto profileDto) {

        log.info("Invoke createProfile method. profileDto: {}, application: {}", profileDto, application);
        profileRepositoryService.createProfile(profileDto, application);
        log.info("Method createProfile successfully finished.");
    }

    @Operation(summary = "Получение аккаунта.",
            description = "Получение информации о аккаунте по id."
    )
    @GetMapping("/get/{profileId}")
    public ProfileDto getProfileById(@PathVariable("profileId") @Parameter(description = "ID профиля",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") String accountId) {
        log.info("Invoke getProfileById method. profileId: {}", accountId);
        ProfileDto profileDto = profileRepositoryService.getProfileById(UUID.fromString(accountId));
        log.info("Method getProfileById return profileDto: {}", profileDto);
        return profileDto;
    }

    @Operation(summary = "Поиск аккаунта по значениям.",
            description = "Происходит поиск аккаунта по полям [фамилия, имя, отчество, телефон, email]"
    )
    @GetMapping("/search")
    public List<ProfileDto> searchProfile(@ModelAttribute ProfileDto profileDto) {
        log.info("Invoke searchProfile method. profileDto: {}", profileDto);
        List<ProfileDto> profileDtoList = profileRepositoryService.searchProfile(profileDto);
        log.info("Method searchProfile return find profileDtoList: {}", profileDtoList);
        return profileDtoList;
    }
}
