package ru.neoflex.jd.entity;

import lombok.Value;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class Tariff {
    private UUID id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private UUID productId;
    private Double rate;

}
