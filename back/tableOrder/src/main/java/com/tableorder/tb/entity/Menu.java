package com.tableorder.tb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "menu_seq_gen", sequenceName = "menu_seq",initialValue = 1, allocationSize = 1)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq_gen")
    private Long menuNo;
    @Column(nullable = false)
    private String menuName;
    @Column(nullable = false)
    private Integer price;
    private String imageUrl;
    private Boolean available = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private MenuCategory category;
}
