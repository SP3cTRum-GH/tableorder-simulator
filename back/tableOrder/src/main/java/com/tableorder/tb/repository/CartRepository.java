package com.tableorder.tb.repository;

import com.tableorder.tb.entity.Cart;
import com.tableorder.tb.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByTable(TableEntity table);
}
