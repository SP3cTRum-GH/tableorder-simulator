package com.tableorder.tb.service;

import com.tableorder.tb.DTO.OptionGroupRequestDTO;
import com.tableorder.tb.DTO.OptionGroupResponseDTO;
import com.tableorder.tb.DTO.OptionRequestDTO;
import com.tableorder.tb.DTO.OptionResponseDTO;
import java.util.List;

public interface OptionService {
    OptionGroupResponseDTO createOptionGroup(OptionGroupRequestDTO requestDTO);
    OptionResponseDTO addOptionToGroup(Long groupId, OptionRequestDTO requestDTO);
    void deleteOptionGroup(Long groupId);
    void deleteOption(Long optionId);
    List<OptionGroupResponseDTO> getOptionGroupsByMenu(Long menuNo);
}
