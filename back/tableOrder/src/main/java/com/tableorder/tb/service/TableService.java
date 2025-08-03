package com.tableorder.tb.service;

import com.tableorder.tb.DTO.TableRequestDTO;
import com.tableorder.tb.DTO.TableResponseDTO;

import java.util.List;

public interface TableService {
    TableResponseDTO createTable(TableRequestDTO dto);
    TableResponseDTO getTable(Long tableNo);
    List<TableResponseDTO> getAllTables();
    TableResponseDTO updateTable(Long tableNo, TableRequestDTO dto);
    void deleteTable(Long tableNo);
}
