package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.neoflex.jd.dto.ProfileDtoRequest;
import ru.neoflex.jd.dto.ProfileDtoResponse;
import ru.neoflex.jd.dto.UserInfoDto;
import java.util.List;

@Tag(name = "Profile service", description = "Сервис для создания/поиска аккаунта.")
public interface ProfileControllerSwagger {
    @Operation(summary = "Создание аккаунта.",
            description = "Поступает информация  для создания аккаунта, происходит валидации и сохранение в БД."
    )
    void createProfile(@Parameter(description = "Источник", example = "mobile")
                       @RequestHeader("x-Source") String application,
                       @Valid @RequestBody ProfileDtoRequest profileDtoRequest);

    @Operation(summary = "Получение аккаунта.",
            description = "Получение информации о аккаунте по id."
    )
    ProfileDtoResponse getProfileById(@Parameter(description = "ID профиля",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                      @PathVariable("profileId") String accountId);

    @Operation(summary = "Поиск аккаунта по значениям.",
            description = "Происходит поиск аккаунта по полям [фамилия, имя, отчество, телефон, email]"
    )
    List<ProfileDtoRequest> searchProfile(@ModelAttribute ProfileDtoRequest profileDtoRequest);
    @Operation(summary = "Поиск аккаунта по username.",
            description = "Используется для поиска аккаунта по username."
    )
    UserInfoDto getUserInfo(@PathVariable("username") String username);
}
