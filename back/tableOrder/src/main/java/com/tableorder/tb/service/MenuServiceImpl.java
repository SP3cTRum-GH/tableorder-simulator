package com.tableorder.tb.service;

import com.tableorder.tb.DTO.OptionGroupResponseDTO;
import com.tableorder.tb.DTO.OptionResponseDTO;
import com.tableorder.tb.entity.Option;
import com.tableorder.tb.entity.OptionGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tableorder.tb.DTO.MenuRequestDTO;
import com.tableorder.tb.DTO.MenuResponseDTO;
import com.tableorder.tb.entity.Menu;
import com.tableorder.tb.entity.MenuCategory;
import com.tableorder.tb.repository.MenuCategoryRepository;
import com.tableorder.tb.repository.MenuRepository;

import com.tableorder.tb.entity.MenuOptionGroup;
import com.tableorder.tb.repository.MenuOptionGroupRepository;
import com.tableorder.tb.repository.OptionGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository categoryRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;

    private OptionGroupResponseDTO toDto(OptionGroup optionGroup) {
        List<OptionResponseDTO> optionDtos = new ArrayList<>();
        for (Option option : optionGroup.getOptions()) {
            optionDtos.add(toDto(option));
        }

        return OptionGroupResponseDTO.builder()
                .groupNo(optionGroup.getGroupNo())
                .groupName(optionGroup.getGroupName())
                .isMultiSelect(optionGroup.getIsMultiSelect())
                .options(optionDtos)
                .build();
    }

    private OptionResponseDTO toDto(Option option) {
        return OptionResponseDTO.builder()
                .optionNo(option.getOptionNo())
                .value(option.getValue())
                .priceAdd(option.getPriceAdd())
                .build();
    }

    @Override
    public MenuResponseDTO createMenu(MenuRequestDTO dto) {
        try {
            MenuCategory category = categoryRepository.findById(dto.getCategoryNo()).get();
            Menu menu = Menu.builder()
                    .menuName(dto.getMenuName())
                    .menuPrice(dto.getPrice())
                    .menuImg(dto.getImageUrl())
                    .available(dto.getAvailable())
                    .soldOut(dto.getSoldOut() != null ? dto.getSoldOut() : false)
                    .category(category)
                    .build();

            return toDTO(menuRepository.save(menu));
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("카테고리 없음");
        }
    }

    @Override
    public MenuResponseDTO updateMenu(Long menuNo, MenuRequestDTO dto) {
        try {
            Menu menu = menuRepository.findById(menuNo).get();

            menu.setMenuName(dto.getMenuName());
            menu.setMenuPrice(dto.getPrice());
            menu.setMenuImg(dto.getImageUrl());
            menu.setAvailable(dto.getAvailable());
            menu.setSoldOut(dto.getSoldOut());

            if (!menu.getCategory().getCategoryNo().equals(dto.getCategoryNo())) {
                try {
                    MenuCategory newCategory = categoryRepository.findById(dto.getCategoryNo()).get();
                    menu.setCategory(newCategory);
                } catch (java.util.NoSuchElementException e) {
                    throw new IllegalArgumentException("카테고리 없음");
                }
            }

            return toDTO(menuRepository.save(menu));
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("메뉴 없음");
        }
    }

    @Override
    public void deleteMenu(Long menuNo) {
        menuRepository.deleteById(menuNo);
    }

    @Override
    public List<MenuResponseDTO> getMenusByCategory(Long categoryNo) {
        try {
            MenuCategory category = categoryRepository.findById(categoryNo).get();
            return menuRepository.findByCategory(category).stream()
                    .map(new Function<Menu, MenuResponseDTO>() {
                        @Override
                        public MenuResponseDTO apply(Menu menu) {
                            return toDTO(menu);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("카테고리 없음");
        }
    }

    @Override
    public void addOptionGroupToMenu(Long menuNo, Long groupNo) {
        try {
            Menu menu = menuRepository.findById(menuNo).get();
            OptionGroup optionGroup = optionGroupRepository.findById(groupNo).get();

            MenuOptionGroup menuOptionGroup = new MenuOptionGroup();
            menuOptionGroup.setMenu(menu);
            menuOptionGroup.setGroup(optionGroup);
            menuOptionGroupRepository.save(menuOptionGroup);
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("메뉴 또는 옵션 그룹을 찾을 수 없습니다.");
        }
    }

    @Override
    public void removeOptionGroupFromMenu(Long menuNo, Long groupNo) {
        try {
            Menu menu = menuRepository.findById(menuNo).get();
            OptionGroup optionGroup = optionGroupRepository.findById(groupNo).get();

            List<MenuOptionGroup> menuOptionGroups = menuOptionGroupRepository.findAll();
            for (MenuOptionGroup mog : menuOptionGroups) {
                if (mog.getMenu().getMenuNo().equals(menuNo) && mog.getGroup().getGroupNo().equals(groupNo)) {
                    menuOptionGroupRepository.delete(mog);
                    return;
                }
            }
            throw new IllegalArgumentException("메뉴와 옵션 그룹 연결을 찾을 수 없습니다.");
        } catch (java.util.NoSuchElementException e) {
            throw new IllegalArgumentException("메뉴 또는 옵션 그룹을 찾을 수 없습니다.");
        }
    }

    private MenuResponseDTO toDTO(Menu menu) {
        List<OptionGroupResponseDTO> optionGroupDTOs = new ArrayList<>();
        for (MenuOptionGroup menuOptionGroup : menuOptionGroupRepository.findByMenu(menu)) {
            optionGroupDTOs.add(toDto(menuOptionGroup.getGroup()));
        }

        return MenuResponseDTO.builder()
                .menuNo(menu.getMenuNo())
                .menuName(menu.getMenuName())
                .price(menu.getMenuPrice())
                .imageUrl(menu.getMenuImg())
                .available(menu.getAvailable())
                .soldOut(menu.getSoldOut())
                .categoryNo(menu.getCategory().getCategoryNo())
                .optionGroups(optionGroupDTOs)
                .build();
    }
}