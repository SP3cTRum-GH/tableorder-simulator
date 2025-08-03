package com.tableorder.tb.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tableorder.tb.DTO.CategoryRequestDTO;
import com.tableorder.tb.DTO.CategoryResponseDTO;
import com.tableorder.tb.entity.Member;
import com.tableorder.tb.entity.MenuCategory;
import com.tableorder.tb.repository.MemberRepository;
import com.tableorder.tb.repository.MenuCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository categoryRepository;
    private final MemberRepository userRepository;
    private final ModelMapper modelMapper;

    private CategoryResponseDTO toDTO(MenuCategory category) {
        return CategoryResponseDTO.builder()
                .categoryNo(category.getCategoryNo())
                .name(category.getCategoryName())
                .build();
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        try {
            Member member = userRepository.findById(dto.getMemberNo()).get();
            MenuCategory category = MenuCategory.builder()
                    .categoryName(dto.getName())
                    .member(member)
                    .build();

            return toDTO(categoryRepository.save(category));
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
    }

    @Override
    public CategoryResponseDTO updateCategory(Long categoryNo, CategoryRequestDTO dto) {
        try {
            MenuCategory category = categoryRepository.findById(categoryNo).get();
            category.setCategoryName(dto.getName());
            return toDTO(categoryRepository.save(category));
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("카테고리를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteCategory(Long categoryNo) {
        categoryRepository.deleteById(categoryNo);
    }

    @Override
    public List<CategoryResponseDTO> getCategoriesByMemberNo(Long memberNo) {
        return categoryRepository.findByMember_MemberNo(memberNo).stream()
                .map(new java.util.function.Function<MenuCategory, CategoryResponseDTO>() {
                    @Override
                    public CategoryResponseDTO apply(MenuCategory category) {
                        return toDTO(category);
                    }
                })
                .toList();
    }

    @Override
    public CategoryResponseDTO getCategory(Long categoryNo) {
        try {
            return toDTO(categoryRepository.findById(categoryNo).get());
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("카테고리를 찾을 수 없습니다.");
        }
    }
}