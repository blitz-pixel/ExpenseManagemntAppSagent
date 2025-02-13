package com.example.ExpenseManagementApp.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;


    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public Transaction() {
    }
    public Transaction(BigDecimal amount, Category category, Instant from, String description, Account account, Category.CatType type) {
        this.amount = amount;
        this.category = category;
        this.date = from;
        this.description = description;
        this.account = account;
        this.type = type;
    }

//    public Transaction(Recurringtransaction recurringTransaction) {
//        Transaction transaction = recurringTransaction.getTransaction();
//        this.account = transaction.getAccount();
//        this.date = transaction.getDate();
//        this.type = transaction.getType();
////        this
////        this.setCategory();
//    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    @Size(max = 40)
    @NotNull
    @Column(name = "uuid", nullable = false, length = 40)
    private String uuid = UUID.randomUUID().toString();

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

    public @Size(max = 40) @NotNull String getUuid() {
        return uuid;
    }

    public void setUuid(@Size(max = 40) @NotNull String uuid) {
        this.uuid = uuid;
    }

}