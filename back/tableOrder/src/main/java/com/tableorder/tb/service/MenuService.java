package com.tableorder.tb.service;

import java.util.List;

import com.tableorder.tb.DTO.MenuRequestDTO;
import com.tableorder.tb.DTO.MenuResponseDTO;

public interface MenuService {
	MenuResponseDTO createMenu(MenuRequestDTO dto);

    MenuResponseDTO updateMenu(Long menuNo, MenuRequestDTO dto);

    void deleteMenu(Long menuNo);


    List<MenuResponseDTO> getMenusByCategory(Long categoryNo);

    void addOptionGroupToMenu(Long menuId, Long groupId);

    void removeOptionGroupFromMenu(Long menuId, Long groupId);
}
