package com.tableorder.tb.service;

import com.tableorder.tb.DTO.MemberRequestDTO;
import com.tableorder.tb.DTO.MemberResponseDTO;
import com.tableorder.tb.domain.MemberRole;
import com.tableorder.tb.entity.Member;
import com.tableorder.tb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO createMember(MemberRequestDTO dto) {
        Member member = Member.builder()
                .memberId(dto.getMemberId())
                .memberPw(dto.getMemberPw())
                .memberName(dto.getMemberName())
                .memberRole(MemberRole.valueOf(dto.getMemberRole()))
                .createdAt(LocalDateTime.now())
                .build();
        return toDTO(memberRepository.save(member));
    }

    @Override
    public MemberResponseDTO getMemberByMemberNo(Long memberNo) {
        try {
            return toDTO(memberRepository.findById(memberNo).get());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
    }

    @Override
    public MemberResponseDTO getMemberByMemberId(String memberId) {
        try {
            return toDTO(memberRepository.findByMemberId(memberId).get());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
    }

    @Override
    public MemberResponseDTO updateMember(Long memberNo, MemberRequestDTO dto) {
        try {
            Member member = memberRepository.findById(memberNo).get();
            member.setMemberId(dto.getMemberId());
            member.setMemberPw(dto.getMemberPw());
            member.setMemberName(dto.getMemberName());
            member.setMemberRole(MemberRole.valueOf(dto.getMemberRole()));
            return toDTO(memberRepository.save(member));
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteMember(Long memberNo) {
        memberRepository.deleteById(memberNo);
    }

    private MemberResponseDTO toDTO(Member member) {
        return MemberResponseDTO.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .memberRole(member.getMemberRole().name())
                .build();
    }
}
