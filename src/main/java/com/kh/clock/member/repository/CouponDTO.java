package com.kh.clock.member.repository;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

	private String couponName;
	private int couponPrice;
	private Date couponRegDt;
}
