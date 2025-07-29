package com.tableorder.tb.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tableorder.tb.domain.MemberRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "member_seq_gen", sequenceName = "member_seq", initialValue = 1, allocationSize = 1)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen")
	private Long memberNo;

	@Column(nullable = false, unique = true)
	private String memberId;

	@Column(nullable = false)
	private String memberPw;

	private String memberName;

	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;

	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuCategory> categories = new ArrayList<>();
}