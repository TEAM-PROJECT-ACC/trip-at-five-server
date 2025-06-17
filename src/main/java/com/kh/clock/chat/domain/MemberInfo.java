package com.kh.clock.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfo {
  private String memEmailId;
  private String memNick;
  private int memSq;
  private String memType;
}
