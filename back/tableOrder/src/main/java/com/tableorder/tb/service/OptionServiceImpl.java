package com.tableorder.tb.service;

import com.tableorder.tb.DTO.OptionGroupRequestDTO;
import com.tableorder.tb.DTO.OptionGroupResponseDTO;
import com.tableorder.tb.DTO.OptionRequestDTO;
import com.tableorder.tb.DTO.OptionResponseDTO;
import com.tableorder.tb.entity.Option;
import com.tableorder.tb.entity.OptionGroup;
import com.tableorder.tb.repository.OptionGroupRepository;
import com.tableorder.tb.repository.OptionRepository;
import com.tableorder.tb.repository.MenuRepository;
import com.tableorder.tb.repository.MenuOptionGroupRepository;
import com.tableorder.tb.entity.Menu;
import com.tableorder.tb.entity.MenuOptionGroup;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;

    @Override
    public OptionGroupResponseDTO createOptionGroup(OptionGroupRequestDTO requestDTO) {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setGroupName(requestDTO.getGroupName());
        optionGroup.setIsMultiSelect(requestDTO.getIsMultiSelect());
        OptionGroup savedOptionGroup = optionGroupRepository.save(optionGroup);
        return toDto(savedOptionGroup);
    }

    @Override
    public OptionResponseDTO addOptionToGroup(Long groupId, OptionRequestDTO requestDTO) {
        try {
            OptionGroup optionGroup = optionGroupRepository.findById(groupId).get();
            Option option = new Option();
            option.setValue(requestDTO.getValue());
            option.setPriceAdd(requestDTO.getPriceAdd());
            option.setOptionGroup(optionGroup);
            Option savedOption = optionRepository.save(option);
            return toDto(savedOption);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("해당 옵션 그룹을 찾을 수 없습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteOptionGroup(Long groupId) {
        OptionGroup optionGroup = optionGroupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("OptionGroup not found"));
        
        menuOptionGroupRepository.deleteByGroup(optionGroup);
        
        optionGroupRepository.deleteById(groupId);
    }

    @Override
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }

    @Override
    public List<OptionGroupResponseDTO> getOptionGroupsByMenu(Long menuNo) {
        try {
            Menu menu = menuRepository.findById(menuNo).get();
            List<MenuOptionGroup> menuOptionGroups = menuOptionGroupRepository.findByMenu(menu);
            return menuOptionGroups.stream()
                    .map(menuOptionGroup -> toDto(menuOptionGroup.getGroup()))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            throw new RuntimeException("해당 메뉴를 찾을 수 없습니다.");
        }
    }

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
}
