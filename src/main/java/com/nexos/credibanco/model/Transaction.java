package com.nexos.credibanco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 100,name = "transaction_sequence")
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Card Card;
    private double amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTransaction;
    private String type;

    public Transaction(Long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }
}
