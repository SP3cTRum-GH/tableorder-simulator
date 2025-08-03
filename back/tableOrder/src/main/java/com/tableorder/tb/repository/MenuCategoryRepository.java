package com.tableorder.tb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tableorder.tb.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long>{
	List<MenuCategory> findByMember_MemberNo(Long memberNo);
}
