package ru.neoflex.jd.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.neoflex.jd.GatewayApp;
import ru.neoflex.jd.controller.GatewayControllerImpl;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.dto.enumerated.ProductType;
import java.time.LocalDateTime;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@WireMockTest
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GatewayApp.class)
@TestPropertySource(properties = {
        "integration.service.product.base-url=${mockServerUrl}",
        "integration.service.tariff.base-url=${mockServerUrl}"})
@AutoConfigureMockMvc
public abstract class AbstractSpringContextTest {
    public final static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @Autowired
    GatewayControllerImpl gatewayControllerImpl;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUp(DynamicPropertyRegistry registry) {
        registry.add("mockServerUrl", wireMockExtension::baseUrl);
    }

    @BeforeAll
    public static void initStub() {
        mapper.registerModule(new JavaTimeModule());
    }

    TariffDto getTariffDto() {
        return TariffDto.builder()
                .name("Giga")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .description("Просто крутой тариф")
                .productId(UUID.randomUUID())
                .rate(15.5)
                .build();
    }

    public ProductDto getProductDto() {
        return ProductDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Потребительский кредит")
                .type(ProductType.LOAN)
                .description("Кредит на любые цели")
                .startDate(LocalDateTime.of(2024, 8, 8, 0, 0))
                .endDate(LocalDateTime.of(2039, 1, 1, 0, 0))
                .author(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .build();
    }

    ProductAudDto getProductAuditDto() {
        return ProductAudDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Потребительский кредит")
                .type(ProductType.LOAN)
                .startDate(LocalDateTime.of(2024, 8, 8, 0, 0))
                .endDate(LocalDateTime.of(2039, 1, 1, 0, 0))
                .description("Кредит на любые цели")
                .author(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .version(1L)
                .build();
    }

}
