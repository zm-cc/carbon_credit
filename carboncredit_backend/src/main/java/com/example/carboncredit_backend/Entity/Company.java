package com.example.carboncredit_backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int company_id;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "password")
    private String password;

    @Column(name = "contact_phone")
    private String contact_phone;

    @Column(name = "contact_email")
    private String contact_email;

    @Column(name = "type_id")
    private int type_id;

    @Column(name = "total_emission")
    private int total_emission;

    @Column(name = "carbon_cost")
    private float carbon_cost;
}
