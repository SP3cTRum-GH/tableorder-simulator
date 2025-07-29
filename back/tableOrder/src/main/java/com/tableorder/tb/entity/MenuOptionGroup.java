package com.tableorder.tb.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "menu_option_group_seq_gen", sequenceName = "menu_option_group_seq",initialValue = 1, allocationSize = 1)
public class MenuOptionGroup implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_option_group_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_no")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_no")
    private OptionGroup group;
}