package com.tableorder.tb.service;

import java.util.List;

import com.tableorder.tb.DTO.CategoryRequestDTO;
import com.tableorder.tb.DTO.CategoryResponseDTO;

public interface MenuCategoryService {
	
	CategoryResponseDTO createCategory(CategoryRequestDTO dto);
	
    CategoryResponseDTO updateCategory(Long categoryNo, CategoryRequestDTO dto);
    
    void deleteCategory(Long categoryNo);
    
    List<CategoryResponseDTO> getCategoriesByMemberNo(Long memberNo);
    
    CategoryResponseDTO getCategory(Long categoryNo);
}
