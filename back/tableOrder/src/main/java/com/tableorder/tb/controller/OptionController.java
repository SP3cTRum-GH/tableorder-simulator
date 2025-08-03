package com.tableorder.tb.controller;

import com.tableorder.tb.DTO.OptionGroupRequestDTO;
import com.tableorder.tb.DTO.OptionGroupResponseDTO;
import com.tableorder.tb.DTO.OptionRequestDTO;
import com.tableorder.tb.DTO.OptionResponseDTO;
import com.tableorder.tb.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @PostMapping("/groups")
    public ResponseEntity<OptionGroupResponseDTO> createOptionGroup(@RequestBody OptionGroupRequestDTO requestDTO) {
        return ResponseEntity.ok(optionService.createOptionGroup(requestDTO));
    }

    @PostMapping("/groups/{groupId}/options")
    public ResponseEntity<OptionResponseDTO> addOptionToGroup(@PathVariable Long groupId, @RequestBody OptionRequestDTO requestDTO) {
        return ResponseEntity.ok(optionService.addOptionToGroup(groupId, requestDTO));
    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<Void> deleteOptionGroup(@PathVariable Long groupId) {
        optionService.deleteOptionGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{menuNo}")
    public ResponseEntity<List<OptionGroupResponseDTO>> getOptionGroupsByMenu(@PathVariable Long menuNo) {
        return ResponseEntity.ok(optionService.getOptionGroupsByMenu(menuNo));
    }
}
