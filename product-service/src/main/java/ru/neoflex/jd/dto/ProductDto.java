package ru.neoflex.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import ru.neoflex.jd.dto.enumerated.ProductType;
import ru.neoflex.jd.entity.Tariff;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class ProductDto {
    @Schema(description = "ID", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa5")
    private UUID id;
    @Schema(description = "Название продукта", defaultValue = "Потребительский кредит")
    private String name;
    @Schema(description = "Тип продукта", defaultValue = "LOAN")
    private ProductType type;
    @Schema(description = "Описание продукта", defaultValue = "Кредит на любые цели.")
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Schema(description = "Тариф")
    private Tariff tariff;
    @Schema(description = "Автор", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID author;
}