package com.example.carboncredit_backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "image")
    private String image;

    @Column(name = "email")
    private String email;

    @Column(name = "credit_amount")
    private int credit_amount;

    @Column(name = "exp")
    private int exp;
}
