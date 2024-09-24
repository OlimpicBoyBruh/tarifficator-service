package ru.neoflex.jd.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UserPassword {
    private String password;
}
