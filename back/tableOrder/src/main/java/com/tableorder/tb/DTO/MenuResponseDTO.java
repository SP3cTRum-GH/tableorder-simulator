package com.tableorder.tb.DTO;

import java.util.List;

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
public class MenuResponseDTO {
    private Long menuNo;
    private String menuName;
    private Integer price;
    private String imageUrl;
    private Boolean available;
    private Boolean soldOut;
    private Long categoryNo;
    private List<OptionGroupResponseDTO> optionGroups;
}
