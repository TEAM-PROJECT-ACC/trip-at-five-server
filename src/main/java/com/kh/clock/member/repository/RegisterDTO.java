package com.kh.clock.member.repository;

import lombok.Data;

@Data
public class RegisterDTO {
	private String email;
	private int emailCount;
	private String pwd;
	private String nickName;
	private int nickCount;
	private String tel;
	private String address;
	
}
