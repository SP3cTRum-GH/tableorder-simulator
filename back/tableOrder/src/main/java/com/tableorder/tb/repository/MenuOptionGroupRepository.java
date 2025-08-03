package com.tableorder.tb.repository;

import com.tableorder.tb.entity.MenuOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tableorder.tb.entity.Menu;
import java.util.List;

import com.tableorder.tb.entity.OptionGroup;

public interface MenuOptionGroupRepository extends JpaRepository<MenuOptionGroup, Long> {
    List<MenuOptionGroup> findByMenu(Menu menu);
    void deleteByGroup(OptionGroup group);
}
