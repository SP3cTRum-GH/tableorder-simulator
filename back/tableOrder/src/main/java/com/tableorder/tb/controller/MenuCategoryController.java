package com.tableorder.tb.controller;

import com.tableorder.tb.DTO.CategoryRequestDTO;
import com.tableorder.tb.DTO.CategoryResponseDTO;
import com.tableorder.tb.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(menuCategoryService.createCategory(dto));
    }

    @PutMapping("/{categoryNo}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long categoryNo, @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(menuCategoryService.updateCategory(categoryNo, dto));
    }

    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryNo) {
        menuCategoryService.deleteCategory(categoryNo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list/{memberNo}")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByMemberNo(@PathVariable Long memberNo) {
        return ResponseEntity.ok(menuCategoryService.getCategoriesByMemberNo(memberNo));
    }

    @GetMapping("/{categoryNo}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Long categoryNo) {
        return ResponseEntity.ok(menuCategoryService.getCategory(categoryNo));
    }
}
