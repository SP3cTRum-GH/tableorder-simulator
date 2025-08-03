package com.tableorder.tb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tableorder.tb.entity.Menu;
import com.tableorder.tb.entity.MenuCategory;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	List<Menu> findByCategory(MenuCategory category);
}
