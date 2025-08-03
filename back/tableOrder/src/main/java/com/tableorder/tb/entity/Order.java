package com.tableorder.tb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "orders") // 'order' 예약어 회피
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_seq", allocationSize = 1)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_gen")
	private Long orderNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_no")
	private Cart cart; // 장바구니(=테이블별 담긴 항목 모음)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_no")
	private Menu menu; // 어떤 메뉴를 담았는지

	private Integer quantity; // 수량

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderOption> orderOptions = new ArrayList<>();
}
