package com.example.ExpenseManagementApp.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "recurringtransaction")
public class Recurringtransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recurring_id", nullable = false)
    private Long recurringId;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "transaction_id",nullable = false)
    private Transaction transaction;


    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private RFrequency frequency;

    @Column(name = "Created_date", nullable = false)
    private LocalDate CurrentDate;

    @Column(name = "next_date")
    private LocalDate nextDate;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @Size(max = 40)
    @NotNull
    @Column(name = "uuid", nullable = false, length = 40)
    private String uuid = UUID.randomUUID().toString();

    public Long getRTransactionId() {
        return recurringId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public RFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(RFrequency frequency) {
        this.frequency = frequency;
    }

    public LocalDate getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(LocalDate startDate) {
        this.CurrentDate = startDate;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate endDate) {
        this.nextDate = endDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public enum RFrequency {
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

}