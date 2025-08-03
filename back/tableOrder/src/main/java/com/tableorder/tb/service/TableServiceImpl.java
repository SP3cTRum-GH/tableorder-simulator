package com.tableorder.tb.service;

import com.tableorder.tb.DTO.TableRequestDTO;
import com.tableorder.tb.DTO.TableResponseDTO;
import com.tableorder.tb.entity.Member;
import com.tableorder.tb.entity.TableEntity;
import com.tableorder.tb.repository.MemberRepository;
import com.tableorder.tb.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final MemberRepository memberRepository;

    private TableResponseDTO toDTO(TableEntity tableEntity) {
        return TableResponseDTO.builder()
                .tableNo(tableEntity.getTableNo())
                .tableName(tableEntity.getTableName())
                .memberNo(tableEntity.getMember() != null ? tableEntity.getMember().getMemberNo() : null)
                .build();
    }

    @Override
    public TableResponseDTO createTable(TableRequestDTO dto) {
        try {
            Member member = memberRepository.findById(dto.getMemberNo()).orElseThrow(() -> new NoSuchElementException("Member not found"));
            TableEntity tableEntity = TableEntity.builder()
                    .tableName(dto.getTableName())
                    .member(member)
                    .build();
            return toDTO(tableRepository.save(tableEntity));
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public TableResponseDTO getTable(Long tableNo) {
        return tableRepository.findById(tableNo)
                .map(this::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Table not found"));
    }

    @Override
    public List<TableResponseDTO> getAllTables() {
        return tableRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TableResponseDTO updateTable(Long tableNo, TableRequestDTO dto) {
        try {
            TableEntity tableEntity = tableRepository.findById(tableNo).orElseThrow(() -> new NoSuchElementException("Table not found"));
            tableEntity.setTableName(dto.getTableName());
            return toDTO(tableRepository.save(tableEntity));
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void deleteTable(Long tableNo) {
        tableRepository.deleteById(tableNo);
    }
}
