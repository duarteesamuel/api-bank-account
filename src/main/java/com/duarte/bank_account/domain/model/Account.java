package com.duarte.bank_account.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    private Instant creationDate;

    @Column(name = "account_number", nullable = false, unique = true)
    private Integer accountNumber;

    @NotBlank(message = "The password cannot be empty.")
    @Column(nullable = false)
    @Size(min = 8, max = 100, message = "The password must have between 8 and 16 characters.")
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transfer> sentTransfers = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transfer> receivedTransfers = new ArrayList<>();

}
