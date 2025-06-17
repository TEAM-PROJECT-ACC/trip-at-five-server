package com.kh.clock.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminInfo {
  private String adminEmailId;
  private int adminSq;
  private String inqCtgCd;
  private String memType;
}
