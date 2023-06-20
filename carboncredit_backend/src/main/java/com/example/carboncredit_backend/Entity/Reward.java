package com.example.carboncredit_backend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private int reward_id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "require_credits")
    private int require_credits;

    @Column(name = "require_level")
    private int require_level;

    @Column(name = "inventory")
    private int inventory;
}
