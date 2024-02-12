package com.vit.minibank.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "account", schema = "bank")
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "pincode")
    private String code;

    @Column(name = "balance")
    private BigDecimal balance;

    @JsonIgnore
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @JsonIgnore
    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL)
    private List<Statistic> statistic;
}
