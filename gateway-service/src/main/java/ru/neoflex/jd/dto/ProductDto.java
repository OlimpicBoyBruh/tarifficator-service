package ru.neoflex.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import ru.neoflex.jd.dto.enumerated.ProductType;
import java.time.LocalDateTime;
import java.util.UUID;

@NotNull
@Value
@Builder
public class ProductDto {
    @Schema(description = "ID", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa5")
    @NotNull(message = "Id не может быть null")
    private UUID id;
    @Schema(description = "Название продукта", defaultValue = "Потребительский кредит")
    private String name;
    @Schema(description = "Тип продукта", defaultValue = "LOAN")
    private ProductType type;
    @Schema(description = "Описание продукта", defaultValue = "Кредит на любые цели")
    private String description;
    @Schema(description = "Дата начала действия продукта", defaultValue = "2024-08-08T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "Дата окончания действия продукта", defaultValue = "2039-01-01T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "Тариф")
    private TariffDto tariff;
    @Schema(description = "Автор", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID author;
}
