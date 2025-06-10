package com.kh.clock.member.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	
	private String email;
	private String pwd;
	private String nickName;
	private String snsUid;
	private String platformName;


}
