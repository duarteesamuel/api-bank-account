package com.duarte.bank_account.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private String id;

    @NotBlank(message = "The name cannot be empty.")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email(message = "Please, enter a valid email address.")
    @NotBlank(message = "The email cannot be empty.")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "account_number", nullable = false, unique = true)
    private Integer accountNumber;

    @NotBlank(message = "The password cannot be empty.")
    @Column(nullable = false)
    @Size(min = 8, max = 100, message = "The password must have between 8 and 16 characteres.")
    private String password;

    @Column(nullable = false)
    private BigDecimal balance;
}
