package com.kh.clock.member.repository;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSelectDTO {

	private String resCd;	  // 예약코드 
	private String resName;   // 예약자 명
	private String resPhone;  // 예약자 연락처
	private Date checkInDt;   // 예약 날짜
	private String ckResSt;   // 예약상태 (CANCEL PROCESSING COMPLETED)
	private String accomName;   // 예약 숙소 이름
	private String roomName;  // 예약 객실 이름
	private String roomNo;  //  객실번호
}
