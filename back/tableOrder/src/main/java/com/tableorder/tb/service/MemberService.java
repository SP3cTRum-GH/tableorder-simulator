package com.tableorder.tb.service;

import com.tableorder.tb.DTO.MemberRequestDTO;
import com.tableorder.tb.DTO.MemberResponseDTO;

public interface MemberService {
    MemberResponseDTO createMember(MemberRequestDTO dto);
    MemberResponseDTO getMemberByMemberNo(Long memberNo);
    MemberResponseDTO getMemberByMemberId(String memberId);
    MemberResponseDTO updateMember(Long memberNo, MemberRequestDTO dto);
    void deleteMember(Long memberNo);
}
