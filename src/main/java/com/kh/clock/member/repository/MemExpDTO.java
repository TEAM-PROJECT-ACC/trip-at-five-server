package com.kh.clock.member.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemExpDTO {
  private String memNo;
  private String resCode;
  private int memExp;
}
