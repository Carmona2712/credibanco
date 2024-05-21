package com.nexos.credibanco.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@Data
@Builder
@Log4j2
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 1,name = "card_sequence")
    private Long id;
    @Column(unique = true,length = 16)
    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar vacío")
    private String cardNumber;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100,nullable = true)
    private String firstLastname;
    @Column(length = 20)
    private String status;
    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar vacío")
    private String expirationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    private double balance;

    @OneToMany
    private List<Transaction> transactionList;

    public Card() {
        this.transactionList = new ArrayList<>();
    }

    /**
     * Método que se ejecuta antes de que la entidad sea persistida en la base de datos.
     * Establece el valor del campo 'createAt' a la fecha y hora actuales.
     */
    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }



}
