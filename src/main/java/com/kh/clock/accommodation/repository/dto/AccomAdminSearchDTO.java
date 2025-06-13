package com.kh.clock.accommodation.repository.dto;

import lombok.Data;

@Data
public class AccomAdminSearchDTO {
  private String accomName;  // 숙박업소명
  private String locName;    // 지역명
  private String keyword;
}
