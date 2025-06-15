package com.kh.clock.member.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancel {

	
	private int userMemSq; //유저 번호   
	private int roomNo;    //객실 번호
}

