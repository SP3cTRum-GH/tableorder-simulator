package com.tableorder.tb.controller;

import com.tableorder.tb.DTO.MemberRequestDTO;
import com.tableorder.tb.DTO.MemberResponseDTO;
import com.tableorder.tb.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO dto) {
        return ResponseEntity.ok(memberService.createMember(dto));
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberResponseDTO> getMemberByMemberNo(@PathVariable Long memberNo) {
        return ResponseEntity.ok(memberService.getMemberByMemberNo(memberNo));
    }

    @GetMapping("/id/{memberId}")
    public ResponseEntity<MemberResponseDTO> getMemberByMemberId(@PathVariable String memberId) {
        return ResponseEntity.ok(memberService.getMemberByMemberId(memberId));
    }

    @PutMapping("/{memberNo}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long memberNo, @RequestBody MemberRequestDTO dto) {
        return ResponseEntity.ok(memberService.updateMember(memberNo, dto));
    }

    @DeleteMapping("/{memberNo}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberNo) {
        memberService.deleteMember(memberNo);
        return ResponseEntity.noContent().build();
    }
}
