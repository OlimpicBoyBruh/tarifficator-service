package ru.neoflex.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import ru.neoflex.jd.valid.Validate;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Данные для создания профиля")
public class ProfileDto {
    @Schema(description = "Уникальный идентификатор пользователя", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @NotNull(groups = {Validate.BankGroup.class, Validate.GosUslugiGroup.class}, message = "Идентифкатор банка не указан")
    @Schema(description = "Идентификатор банка.", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID bankId;
    @NotNull(groups = {Validate.BankGroup.class, Validate.GosUslugiGroup.class}, message = "Фамилия не указана")
    @Schema(description = "Фамилия", defaultValue = "Мещеряков")
    private String lastName;
    @NotNull(groups = {Validate.MailGroup.class, Validate.BankGroup.class, Validate.GosUslugiGroup.class}, message = "Имя не указано")
    @Schema(description = "Имя", defaultValue = "Артём")
    private String firstName;
    @NotNull(groups = {Validate.BankGroup.class, Validate.GosUslugiGroup.class}, message = "Отчество не указано")
    @Schema(description = "Отчество", defaultValue = "Александрович")
    private String middleName;
    @NotNull(groups = {Validate.BankGroup.class, Validate.GosUslugiGroup.class}, message = "Дата рождения не указана")
    @Schema(description = "Дата рождения", defaultValue = "2000-04-03")
    private LocalDate dateOfBirth;
    @NotNull(groups = {Validate.BankGroup.class, Validate.GosUslugiGroup.class}, message = "Серия и номер паспорта не указаны")
    @Pattern(regexp = "^\\d{4} \\d{6}$", message = "Некорректный формат серии/номера паспорта")
    @Schema(description = "Номер паспорта", defaultValue = "1234 567890")
    private String passportNumber;
    @NotNull(groups = {Validate.GosUslugiGroup.class}, message = "Место рождения не указано")
    @Schema(description = "Место рождения", defaultValue = "г.Москва")
    private String birthPlace;
    @NotNull(groups = {Validate.MobileGroup.class, Validate.GosUslugiGroup.class}, message = "Телефон не указан")
    @Schema(description = "Номер телефона", defaultValue = "7123456789")
    @Pattern(regexp = "^7\\d{9}$", message = "Номер телефона должен начинаться с 7 и содержать 10 цифр")
    private String phoneNumber;
    @NotNull(groups = {Validate.MailGroup.class}, message = "E-mail не указан.")
    @Schema(description = "E-mail", defaultValue = "test@test.ru")
    @Pattern(regexp = "^[\\w.+\\-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = "Некорректный формат email")
    private String email;
    @NotNull(groups = {Validate.GosUslugiGroup.class}, message = "Адрес регистрации не указан")
    @Schema(description = "Адрес регистрации", defaultValue = "г.Москва, ул.Московская, д.1")
    private String registrationAddress;
    @Schema(description = "Адрес проживания", defaultValue = "г.Москва, ул.Коновалова, д.1")
    private String residenceAddress;
}
