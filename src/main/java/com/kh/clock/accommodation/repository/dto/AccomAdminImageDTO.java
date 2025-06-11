package com.kh.clock.accommodation.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccomAdminImageDTO {
  private String accomImgHashCd;
  private String accomImgOrgName;
  private String accomImgPathName;
  private int accomNo;
}
