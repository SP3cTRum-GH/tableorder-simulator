package com.tableorder.tb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "option_group_seq_gen", sequenceName = "option_group_seq",initialValue = 1, allocationSize = 1)
public class OptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_group_seq_gen")
    private Long groupNo;

    @Column(nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();
}