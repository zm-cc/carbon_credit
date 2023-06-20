package com.example.carboncredit_backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "company_types")
public class CompanyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int type_id;

    @Column(name = "type_name")
    private String type_name;

    @Column(name = "distance_rate")
    private float distance_rate;

    @Column(name = "credit_rate")
    private float credit_rate;

    @Column(name = "type_photo")
    private String type_photo;
}
