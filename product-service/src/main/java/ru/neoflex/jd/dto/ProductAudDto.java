package ru.neoflex.jd.dto;

import lombok.Data;
import ru.neoflex.jd.dto.enumerated.ProductType;
import ru.neoflex.jd.entity.Tariff;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductAudDto {
    private UUID id;
    private String name;
    private ProductType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private Tariff tariff;
    private UUID author;
    private Long version;
}
