package com.example.carboncredit_backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "travel_records")
public class TravelRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int record_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "company_id")
    private int company_id;

    @Column(name = "duration")
    private int duration;

    @Column(name = "travel_distance")
    private int travel_distance;

    @Column(name = "credits")
    private int credits;
}
