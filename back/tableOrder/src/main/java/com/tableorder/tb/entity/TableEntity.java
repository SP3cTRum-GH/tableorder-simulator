package com.tableorder.tb.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@SequenceGenerator(name = "table_seq_gen", sequenceName = "table_seq", allocationSize = 1)
public class TableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_seq_gen")
	private Long tableNo;

	private String tableName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@OneToOne(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
	private Cart cart;
}