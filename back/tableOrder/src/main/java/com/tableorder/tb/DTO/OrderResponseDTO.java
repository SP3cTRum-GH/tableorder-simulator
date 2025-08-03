package com.tableorder.tb.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderNo;
    private Long menuNo;
    private String menuName;
    private Integer menuPrice;
    private Integer quantity;
    private List<OrderOptionResponseDTO> orderOptions;
}
