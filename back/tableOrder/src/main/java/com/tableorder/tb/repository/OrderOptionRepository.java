package com.tableorder.tb.repository;

import com.tableorder.tb.entity.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOptionRepository extends JpaRepository<OrderOption, Long> {
}
