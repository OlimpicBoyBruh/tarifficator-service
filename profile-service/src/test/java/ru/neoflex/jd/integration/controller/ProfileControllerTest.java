package ru.neoflex.jd.integration.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.neoflex.jd.dto.ProfileDtoRequest;
import ru.neoflex.jd.service.ProfileRepositoryService;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;
import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ProfileRepositoryService profileRepositoryService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.8-alpine"));

    @BeforeAll
    public static void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @DynamicPropertySource
    static void configuration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

    }

    @Test
    void createProfileSuccessTest() throws Exception {
        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mobile")
                        .content(objectMapper.writeValueAsString(getProfileDto())))
                .andExpect(status().isOk());
        ProfileDtoRequest profileDtoRequest = profileRepositoryService.searchProfile(getProfileDto()).get(0);
        assertAll(
                () -> assertNotNull(profileDtoRequest.getId()),
                () -> assertEquals(getProfileDto().getBankId(), profileDtoRequest.getBankId()),
                () -> assertEquals(getProfileDto().getLastName(), profileDtoRequest.getLastName()),
                () -> assertEquals(getProfileDto().getFirstName(), profileDtoRequest.getFirstName()),
                () -> assertEquals(getProfileDto().getMiddleName(), profileDtoRequest.getMiddleName()),
                () -> assertEquals(getProfileDto().getDateOfBirth(), profileDtoRequest.getDateOfBirth()),
                () -> assertEquals(getProfileDto().getPassportNumber(), profileDtoRequest.getPassportNumber()),
                () -> assertEquals(getProfileDto().getBirthPlace(), profileDtoRequest.getBirthPlace()),
                () -> assertEquals(getProfileDto().getPhoneNumber(), profileDtoRequest.getPhoneNumber()),
                () -> assertEquals(getProfileDto().getEmail(), profileDtoRequest.getEmail()),
                () -> assertEquals(getProfileDto().getRegistrationAddress(), profileDtoRequest.getRegistrationAddress()),
                () -> assertEquals(getProfileDto().getResidenceAddress(), profileDtoRequest.getResidenceAddress())
        );

    }

    @Test
    void getProfileSuccessTest() throws Exception {
        String uuid = String.valueOf(profileRepositoryService.createProfile(getProfileDto(), "mobile").getId());

        mockMvc.perform(get("/profile/get/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void searchProfileSuccessTest() throws Exception {
        profileRepositoryService.createProfile(getProfileDto(), "mobile");

        mockMvc.perform(get("/profile/search?phoneNumber=7123456789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProfileExceptionTest() throws Exception {
        mockMvc.perform(get("/profile/get/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mobile")
                        .content(objectMapper.writeValueAsString(getProfileDto())))
                .andExpect(status().isNotFound()).andDo(result -> assertEquals(NoSuchElementException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileMissingRequestHeaderExceptionTest() throws Exception {
        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getProfileDto())))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(MissingRequestHeaderException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileMissingRequestBodyExceptionTest() throws Exception {
        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mobile")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(HttpMessageNotReadableException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidMobileExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = getProfileDto();
        profileDtoRequest.setPhoneNumber(null);
        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mobile")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(IllegalArgumentException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidMailExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = ProfileDtoRequest.builder()
                .id(null)
                .build();

        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mail")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(IllegalArgumentException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidBankExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = ProfileDtoRequest.builder()
                .id(null)
                .build();

        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "bank")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(IllegalArgumentException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidGosUslugiExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = ProfileDtoRequest.builder()
                .id(null)
                .build();

        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "gosuslugi")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(IllegalArgumentException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidHeaderExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = ProfileDtoRequest.builder()
                .id(null)
                .build();

        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "test")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(IllegalArgumentException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidPhoneNumberExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = ProfileDtoRequest.builder()
                .phoneNumber("123")
                .build();

        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mobile")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(MethodArgumentNotValidException.class,
                        result.getResolvedException().getClass()));
    }

    @Test
    void createProfileFailValidPassportNumberExceptionTest() throws Exception {
        ProfileDtoRequest profileDtoRequest = ProfileDtoRequest.builder()
                .passportNumber("123")
                .build();

        mockMvc.perform(post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Source", "mobile")
                        .content(objectMapper.writeValueAsString(profileDtoRequest)))
                .andExpect(status().isBadRequest())
                .andDo(result -> assertEquals(MethodArgumentNotValidException.class,
                        result.getResolvedException().getClass()));
    }


    private ProfileDtoRequest getProfileDto() {
        return ProfileDtoRequest.builder()
                .bankId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .lastName("Иванов")
                .firstName("Иван")
                .middleName("Иванович")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .passportNumber("1234 567890")
                .birthPlace("Москва")
                .phoneNumber("7123456789")
                .email("ivan.ivanov@example.com")
                .registrationAddress("ул. Пушкина, д. 1")
                .residenceAddress("ул. Лермонтова, д. 2")
                .build();
    }
}
