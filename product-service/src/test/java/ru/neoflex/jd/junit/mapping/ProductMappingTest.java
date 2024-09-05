package ru.neoflex.jd.junit.mapping;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.dto.enumerated.ProductType;
import ru.neoflex.jd.entity.Product;
import ru.neoflex.jd.entity.Tariff;
import ru.neoflex.jd.mapping.ProductMapping;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMappingTest {
    private final ProductMapping productMapping = Mappers.getMapper(ProductMapping.class);
    private static final String SAMPLE_PRODUCT_NAME = "Sample Product";
    private static final ProductType SAMPLE_PRODUCT_TYPE = ProductType.CARD;
    private static final String SAMPLE_PRODUCT_DESCRIPTION = "This is a sample product description.";
    private static final UUID UNIVERSAL_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5");
    private static final String TARIFF_NAME = "Giga";
    private static final String TARIFF_DESCRIPTION = "Просто крутой тариф";
    private static final double TARIFF_RATE = 15.5;
    private static final LocalDateTime UNIVERSAL_TIME = LocalDateTime
            .of(2025, 12, 12, 12, 12);

    @Test
    void toDtoTest() {
        ProductDto productDto = productMapping.toDto(getProduct());

        assertAll(
                () -> assertEquals(SAMPLE_PRODUCT_NAME, productDto.getName()),
                () -> assertEquals(SAMPLE_PRODUCT_TYPE, productDto.getType()),
                () -> assertEquals(SAMPLE_PRODUCT_DESCRIPTION, productDto.getDescription()),
                () -> assertEquals(UNIVERSAL_ID, productDto.getAuthor()),
                () -> assertEquals(UNIVERSAL_TIME, productDto.getStartDate()),
                () -> assertEquals(UNIVERSAL_TIME, productDto.getEndDate()),
                () -> assertEquals(TARIFF_NAME, productDto.getTariff().getName()),
                () -> assertEquals(TARIFF_RATE, productDto.getTariff().getRate()),
                () -> assertEquals(TARIFF_DESCRIPTION, productDto.getTariff().getDescription()),
                () -> assertEquals(UNIVERSAL_ID, productDto.getAuthor()),
                () -> assertEquals(UNIVERSAL_TIME, productDto.getTariff().getStartDate()),
                () -> assertEquals(UNIVERSAL_TIME, productDto.getTariff().getEndDate()),
                () -> assertEquals(UNIVERSAL_ID, productDto.getTariff().getProductId()),
                () -> assertEquals(TARIFF_RATE, productDto.getTariff().getRate())
        );
    }

    @Test
    void toEntityTest() {
        Product product = productMapping.toEntity(getProductDto());

        assertAll(
                () -> assertEquals(SAMPLE_PRODUCT_NAME, product.getName()),
                () -> assertEquals(SAMPLE_PRODUCT_TYPE, product.getType()),
                () -> assertEquals(SAMPLE_PRODUCT_DESCRIPTION, product.getDescription()),
                () -> assertEquals(UNIVERSAL_ID, product.getAuthor()),
                () -> assertEquals(UNIVERSAL_TIME, product.getStartDate()),
                () -> assertEquals(UNIVERSAL_TIME, product.getEndDate()),
                () -> assertEquals(TARIFF_NAME, product.getTariff().getName()),
                () -> assertEquals(TARIFF_RATE, product.getTariff().getRate()),
                () -> assertEquals(TARIFF_DESCRIPTION, product.getTariff().getDescription()),
                () -> assertEquals(UNIVERSAL_ID, product.getAuthor()),
                () -> assertEquals(UNIVERSAL_TIME, product.getTariff().getStartDate()),
                () -> assertEquals(UNIVERSAL_TIME, product.getTariff().getEndDate()),
                () -> assertEquals(UNIVERSAL_ID, product.getTariff().getProductId()),
                () -> assertEquals(TARIFF_RATE, product.getTariff().getRate())
        );
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
}