package com.tableorder.tb.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderOptionResponseDTO {
    private Long orderOptionNo;
    private Long optionNo;
    private String optionValue;
    private Integer optionPrice;
}
