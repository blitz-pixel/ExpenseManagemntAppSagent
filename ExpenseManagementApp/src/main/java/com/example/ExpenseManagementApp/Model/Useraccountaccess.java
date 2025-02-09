package com.example.ExpenseManagementApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "useraccountaccess")
public class Useraccountaccess {
//    @EmbeddedId
//    private UseraccountaccessId id;

    @Id
    @Column(name = "shared_account_id", nullable = false)
    private Long shared_account_id;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Account_id", nullable = false)
    private Account Account;

    @ColumnDefault("'ADMIN'")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Rolee role;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    private enum Rolee {
        ADMIN,
        USER,
        VIEWER
    }

}