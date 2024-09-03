package ru.neoflex.jd.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Audited
public class Tariff {
    @Id
    private UUID id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private UUID productId;
    private Double rate;
}
