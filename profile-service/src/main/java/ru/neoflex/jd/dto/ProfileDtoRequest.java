package ru.neoflex.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Данные для создания профиля")
public class ProfileDtoRequest {
    @Schema(description = "Уникальный идентификатор пользователя", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "Идентификатор банка.", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID bankId;
    @Schema(description = "Фамилия", defaultValue = "Мещеряков")
    private String lastName;
    @Schema(description = "Имя", defaultValue = "Артём")
    private String firstName;
    @Schema(description = "Отчество", defaultValue = "Александрович")
    private String middleName;
    @Schema(description = "Дата рождения", defaultValue = "2000-04-03")
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Некорректный формат серии/номера паспорта")
    @Schema(description = "Номер паспорта", defaultValue = "1234 567890")
    private String passportNumber;
    @Schema(description = "Место рождения", defaultValue = "г.Москва")
    private String birthPlace;
    @Schema(description = "Номер телефона", defaultValue = "7123456789")
    @Pattern(regexp = "^7\\d{9}$", message = "Номер телефона должен начинаться с 7 и содержать 10 цифр")
    private String phoneNumber;
    @Schema(description = "E-mail", defaultValue = "test@test.ru")
    @Pattern(regexp = "^[\\w.+\\-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = "Некорректный формат email")
    private String email;
    @Schema(description = "Адрес регистрации", defaultValue = "г.Москва, ул.Московская, д.1")
    private String registrationAddress;
    @Schema(description = "Адрес проживания", defaultValue = "г.Москва, ул.Коновалова, д.1")
    private String residenceAddress;
    @Schema(description = "Имя пользователя", defaultValue = "olimpicboy")
    @NotNull(message = "Необходимо указать username")
    private String username;
    @Schema(description = "Пароль", defaultValue = "12345678")
    @NotNull(message = "Необходимо указать password")
    private String password;
}
