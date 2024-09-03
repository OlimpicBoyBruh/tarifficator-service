package ru.neoflex.jd.junit.mapping;


import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.neoflex.jd.dto.ProfileDto;
import ru.neoflex.jd.entity.Profile;
import ru.neoflex.jd.mapping.ProfileMapper;
import java.time.LocalDate;
import java.util.UUID;
import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProfileMapperTest {

    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    private static final UUID DEFAULT_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    private static final UUID DEFAULT_BANK_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    private static final String DEFAULT_LAST_NAME = "Иванов";
    private static final String DEFAULT_FIRST_NAME = "Иван";
    private static final String DEFAULT_MIDDLE_NAME = "Иванович";
    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.of(1990, 1, 1);
    private static final String DEFAULT_PASSPORT_NUMBER = "1234 567890";
    private static final String DEFAULT_BIRTH_PLACE = "Москва";
    private static final String DEFAULT_PHONE_NUMBER = "71234567890";
    private static final String DEFAULT_EMAIL = "ivan.ivanov@example.com";
    private static final String DEFAULT_REGISTRATION_ADDRESS = "ул. Пушкина, д. 1";
    private static final String DEFAULT_RESIDENCE_ADDRESS = "ул. Лермонтова, д. 2";


    @Test
    void toDtoTest() {
        ProfileDto profileDto = profileMapper.toDto(getProfile());

        assertAll(
                () -> assertEquals(DEFAULT_ID, profileDto.getId()),
                () -> assertEquals(DEFAULT_BANK_ID, profileDto.getBankId()),
                () -> assertEquals(DEFAULT_LAST_NAME, profileDto.getLastName()),
                () -> assertEquals(DEFAULT_FIRST_NAME, profileDto.getFirstName()),
                () -> assertEquals(DEFAULT_MIDDLE_NAME, profileDto.getMiddleName()),
                () -> assertEquals(DEFAULT_DATE_OF_BIRTH, profileDto.getDateOfBirth()),
                () -> assertEquals(DEFAULT_PASSPORT_NUMBER, profileDto.getPassportNumber()),
                () -> assertEquals(DEFAULT_BIRTH_PLACE, profileDto.getBirthPlace()),
                () -> assertEquals(DEFAULT_PHONE_NUMBER, profileDto.getPhoneNumber()),
                () -> assertEquals(DEFAULT_EMAIL, profileDto.getEmail()),
                () -> assertEquals(DEFAULT_REGISTRATION_ADDRESS, profileDto.getRegistrationAddress()),
                () -> assertEquals(DEFAULT_RESIDENCE_ADDRESS, profileDto.getResidenceAddress())
        );

    }

    @Test
    void toEntityTest() {
        Profile profile = profileMapper.toEntity(getProfileDto());

        assertAll(
                () -> assertEquals(DEFAULT_ID, profile.getId()),
                () -> assertEquals(DEFAULT_BANK_ID, profile.getBankId()),
                () -> assertEquals(DEFAULT_LAST_NAME, profile.getLastName()),
                () -> assertEquals(DEFAULT_FIRST_NAME, profile.getFirstName()),
                () -> assertEquals(DEFAULT_MIDDLE_NAME, profile.getMiddleName()),
                () -> assertEquals(DEFAULT_DATE_OF_BIRTH, profile.getDateOfBirth()),
                () -> assertEquals(DEFAULT_PASSPORT_NUMBER, profile.getPassportNumber()),
                () -> assertEquals(DEFAULT_BIRTH_PLACE, profile.getBirthPlace()),
                () -> assertEquals(DEFAULT_PHONE_NUMBER, profile.getPhoneNumber()),
                () -> assertEquals(DEFAULT_EMAIL, profile.getEmail()),
                () -> assertEquals(DEFAULT_REGISTRATION_ADDRESS, profile.getRegistrationAddress()),
                () -> assertEquals(DEFAULT_RESIDENCE_ADDRESS, profile.getResidenceAddress())
        );
    }


    private static Profile getProfile() {
        return Profile.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .bankId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .lastName("Иванов")
                .firstName("Иван")
                .middleName("Иванович")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .passportNumber("1234 567890")
                .birthPlace("Москва")
                .phoneNumber("71234567890")
                .email("ivan.ivanov@example.com")
                .registrationAddress("ул. Пушкина, д. 1")
                .residenceAddress("ул. Лермонтова, д. 2")
                .build();
    }

    private static ProfileDto getProfileDto() {
        return ProfileDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .bankId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .lastName("Иванов")
                .firstName("Иван")
                .middleName("Иванович")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .passportNumber("1234 567890")
                .birthPlace("Москва")
                .phoneNumber("71234567890")
                .email("ivan.ivanov@example.com")
                .registrationAddress("ул. Пушкина, д. 1")
                .residenceAddress("ул. Лермонтова, д. 2")
                .build();
    }
}
