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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.neoflex.jd.ProductApp;
import ru.neoflex.jd.controller.ProductController;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.dto.enumerated.ProductType;
import ru.neoflex.jd.entity.Product;
import ru.neoflex.jd.entity.Tariff;
import ru.neoflex.jd.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@DirtiesContext
@AutoConfigureMockMvc
@Testcontainers
@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductApp.class)
@TestPropertySource(properties = "integration.service.tariff.base-url=${mockServerUrl}")
public abstract class AbstractSpringContextTest {
    public final static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.8-alpine"));

    @Autowired
    ProductController productController;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUp(DynamicPropertyRegistry registry) {
        registry.add("mockServerUrl", wireMockExtension::baseUrl);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    public static void initStub() {
        mapper.registerModule(new JavaTimeModule());
    }

    ProductDto getProductDto() {
        return ProductDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Sample Product")
                .type(ProductType.CARD)
                .startDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .endDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .description("This is a sample product description.")
                .tariff(getTariffDto())
                .author(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .build();
    }

    Tariff getTariff() {
        return Tariff.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Giga")
                .startDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .endDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .description("Просто крутой тариф")
                .productId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .rate(15.5)
                .build();
    }

    Product getProduct() {
        return Product.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Sample Product")
                .type(ProductType.CARD)
                .startDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .endDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .description("This is a sample product description.")
                .tariff(getTariff())
                .author(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .build();
    }

    TariffDto getTariffDto() {
        return TariffDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Giga")
                .startDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .endDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .description("Просто крутой тариф")
                .productId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .rate(15.5)
                .build();
    }
}
