package com.tableorder.tb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "menu_seq_gen", sequenceName = "menu_seq", allocationSize = 1)
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq_gen")
	private Long menuNo;

	@Column(nullable = false)
	private String menuName;

	@Column(nullable = false)
	private Integer menuPrice;

	private String menuImg; // 이미지 경로/URL
	private Boolean soldOut = false;
	private Boolean available = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_no")
	private MenuCategory category;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuOptionGroup> optionGroups = new ArrayList<>();
}
