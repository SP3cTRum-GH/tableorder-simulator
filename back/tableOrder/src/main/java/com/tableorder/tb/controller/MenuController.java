package com.tableorder.tb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tableorder.tb.DTO.MenuRequestDTO;
import com.tableorder.tb.DTO.MenuResponseDTO;
import com.tableorder.tb.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponseDTO> create(@RequestBody MenuRequestDTO dto) {
        return ResponseEntity.ok(menuService.createMenu(dto));
    }

    @PutMapping("/{menuNo}")
    public ResponseEntity<MenuResponseDTO> update(@PathVariable Long menuNo, @RequestBody MenuRequestDTO dto) {
        return ResponseEntity.ok(menuService.updateMenu(menuNo, dto));
    }

    @DeleteMapping("/{menuNo}")
    public ResponseEntity<Void> delete(@PathVariable Long menuNo) {
        menuService.deleteMenu(menuNo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryNo}")
    public ResponseEntity<List<MenuResponseDTO>> getByCategory(@PathVariable Long categoryNo) {
        return ResponseEntity.ok(menuService.getMenusByCategory(categoryNo));
    }

    @PostMapping("/{menuNo}/option-groups/{groupNo}")
    public ResponseEntity<Void> addOptionGroupToMenu(@PathVariable Long menuNo, @PathVariable Long groupNo) {
        menuService.addOptionGroupToMenu(menuNo, groupNo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{menuNo}/option-groups/{groupNo}")
    public ResponseEntity<Void> removeOptionGroupFromMenu(@PathVariable Long menuNo, @PathVariable Long groupNo) {
        menuService.removeOptionGroupFromMenu(menuNo, groupNo);
        return ResponseEntity.noContent().build();
    }
}
