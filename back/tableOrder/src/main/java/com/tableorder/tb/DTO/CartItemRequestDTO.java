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
public class CartItemRequestDTO {
    private Long tableNo; 
    private Long menuNo; 
    private Integer quantity; 
    private List<Long> selectedOptionNos;
}
