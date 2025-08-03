package com.tableorder.tb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SequenceGenerator(name = "order_opt_seq_gen", sequenceName = "order_opt_seq", allocationSize = 1)
public class OrderOption {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_opt_seq_gen")
	private Long orderOptionNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_no")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_no")
	private Option option;

	// 주문 시점의 옵션가를 확정 기록(옵션 가격이 바뀌어도 과거 데이터 보존)
	private Integer optionPrice;
}
