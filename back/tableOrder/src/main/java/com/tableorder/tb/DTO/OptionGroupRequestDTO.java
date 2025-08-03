package com.tableorder.tb.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionGroupRequestDTO {
    private String groupName;
    private Boolean isMultiSelect;
}
