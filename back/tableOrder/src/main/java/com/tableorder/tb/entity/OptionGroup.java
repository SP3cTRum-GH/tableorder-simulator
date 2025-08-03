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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "opt_group_seq_gen", sequenceName = "opt_group_seq", allocationSize = 1)
public class OptionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opt_group_seq_gen")
	private Long groupNo;

	@Column(nullable = false)
	private String groupName;

	@Column(nullable = false)
	private Boolean isMultiSelect = false;

	@OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Option> options = new ArrayList<>();
}