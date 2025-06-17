package com.kh.clock.member.repository;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

	private String couponName;  // 쿠폰 이름
	private int couponPrice;    // 쿠폰 가격
	private Date couponRegDt;   // 쿠폰 등록 날짜
}
