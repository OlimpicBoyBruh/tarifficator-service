package ru.neoflex.jd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import ru.neoflex.jd.dto.enumerated.ProductType;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Tariff tariff;
    private UUID author;
}
