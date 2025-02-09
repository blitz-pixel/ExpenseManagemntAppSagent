package com.example.ExpenseManagementApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long account_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user_Foriegn_id;

//    @JoinColumn(name = "user_id", nullable = false)
//    private Long user_id;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Setter
    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    @Column(name = "type", nullable = false)
    private AccountType type;


    @Column(name = "created_at",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private Instant createdAt;


    public void setUser_Foriegn_id) {
        return User_Foriegn_id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public enum AccountType {
        personal,
        shared
    }

}