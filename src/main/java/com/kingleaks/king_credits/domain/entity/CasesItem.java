package com.kingleaks.king_credits.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "cases_item")
public class CasesItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "coefficient")
    private Double coefficient;

    @Column(name = "photo_data", columnDefinition="bytea")
    private byte[] photoData;

    @Column(name = "cases_name")
    private String caseName;
}
