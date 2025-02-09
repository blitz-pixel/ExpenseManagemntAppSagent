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
@Table(name = "auditlogs")
public class Auditlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private Useraccountaccess user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "shared_account_id", nullable = false)
    private Useraccountaccess sharedAccount;

    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    @Column(name = "action", nullable = false)
    private ActionType action;

    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @Column(name = "table_name", nullable = false)
    private String tableName;


    @Column(name = "access_time",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" )
    private Instant accessTime;

    public enum ActionType {
        CREATE, UPDATE, DELETE
    }

}