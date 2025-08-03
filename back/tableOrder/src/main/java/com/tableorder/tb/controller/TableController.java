package com.tableorder.tb.controller;

import com.tableorder.tb.DTO.TableRequestDTO;
import com.tableorder.tb.DTO.TableResponseDTO;
import com.tableorder.tb.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping
    public ResponseEntity<TableResponseDTO> createTable(@RequestBody TableRequestDTO dto) {
        return ResponseEntity.ok(tableService.createTable(dto));
    }

    @GetMapping("/{tableNo}")
    public ResponseEntity<TableResponseDTO> getTable(@PathVariable Long tableNo) {
        return ResponseEntity.ok(tableService.getTable(tableNo));
    }

    @GetMapping
    public ResponseEntity<List<TableResponseDTO>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @PutMapping("/{tableNo}")
    public ResponseEntity<TableResponseDTO> updateTable(@PathVariable Long tableNo, @RequestBody TableRequestDTO dto) {
        return ResponseEntity.ok(tableService.updateTable(tableNo, dto));
    }

    @DeleteMapping("/{tableNo}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long tableNo) {
        tableService.deleteTable(tableNo);
        return ResponseEntity.noContent().build();
    }
}
