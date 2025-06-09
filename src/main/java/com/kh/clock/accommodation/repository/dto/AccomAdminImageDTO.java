package com.kh.clock.accommodation.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccomAdminImageDTO {
  private String accomImageHashCd;
  private String accomImageOrgName;
  private String accomImagePathName;
  private int accomSq;
}
