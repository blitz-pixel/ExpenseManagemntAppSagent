package com.example.ExpenseManagementApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Category.CatType type;

    @Column(name = "description")
    private String description;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
//    @JsonIgnore
    private Category category;

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Instant getDate() {
        return date;
    }

    public Category.CatType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Category.CatType type) {
        this.type = type;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setId(Long id) {
        this.id = id;
    }




}