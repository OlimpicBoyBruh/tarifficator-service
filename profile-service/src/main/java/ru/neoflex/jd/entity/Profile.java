package ru.neoflex.jd.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTable(name = "user_password",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Profile {
    @Id
    private UUID id;
    private UUID bankId;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate dateOfBirth;
    private String passportNumber;
    private String birthPlace;
    private String phoneNumber;
    private String email;
    private String registrationAddress;
    private String residenceAddress;
    private String username;
    @Embedded
    @AttributeOverride(name = "password",
            column = @Column(name = "password", table = "user_password"))
    private UserPassword password;
}
