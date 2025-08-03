package com.tableorder.tb.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberRole;
}
