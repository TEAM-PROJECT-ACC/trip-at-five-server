package com.kh.clock.review.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageDTO {
  private String revImgHashCd;
  private String revImgOrgName;
  private String revImgPathName;
  private int revNo;
}
