package com.tableorder.tb.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionResponseDTO {
    private Long optionNo;
    private String value;
    private Integer priceAdd;
}
