package com.example.ExpenseManagementApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    @Column(name = "budget_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "budget_name", nullable = false)
    private String budgetName;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "budget_start_date", nullable = false)
    private LocalDate budgetStartDate;

    @Column(name = "budget_end_date", nullable = false)
    private LocalDate budgetEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_frequency", nullable = false)
    private Frequency budgetFrequency;

    public Account getAccount() { return account;}
    public void setAccount(Account account) { this.account = account;}

    public String getBudgetName() { return budgetName;}
    public void setBudgetName(String budgetName) { this.budgetName = budgetName;}

    public BigDecimal getAmount() { return amount;}
    public void setAmount(BigDecimal amount) { this.amount = amount;}

    public LocalDate getBudgetStartDate() { return budgetStartDate;}
    public void setBudgetStartDate(LocalDate budgetStartDate) {this.budgetStartDate = budgetStartDate;}

    public LocalDate getBudgetEndDate() { return budgetEndDate;}
    public void setBudgetEndDate(LocalDate budgetEndDate) {this.budgetEndDate = budgetEndDate;}

    public Frequency getBudgetFrequency() { return budgetFrequency;}
    public void setBudgetFrequency(Frequency budgetFrequency) { this.budgetFrequency = budgetFrequency;}

    public enum Frequency {
        MONTHLY,
        YEARLY
    }

    // Helper method to calculate end date based on frequency
    public void calculateEndDate() {
        if (budgetFrequency == Frequency.MONTHLY) {
            this.budgetEndDate = this.budgetStartDate.plusMonths(1);
        } else if (budgetFrequency == Frequency.YEARLY) {
            this.budgetEndDate = this.budgetStartDate.plusYears(1);
        }
    }
}