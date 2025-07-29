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
import jakarta.persistence.Table;

@Entity
@Table(name = "menu_option")
@SequenceGenerator(name = "option_seq_gen", sequenceName = "option_seq",initialValue = 1, allocationSize = 1)
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_seq_gen")
    private Long optionNo;

    @Column(nullable = false)
    private String value;

    private Integer priceAdd = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_no")
    private OptionGroup group;
}
