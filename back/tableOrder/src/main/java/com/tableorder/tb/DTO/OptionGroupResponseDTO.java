package com.tableorder.tb.DTO;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionGroupResponseDTO {
    private Long groupNo;
    private String groupName;
    private Boolean isMultiSelect;
    private List<OptionResponseDTO> options;
}
