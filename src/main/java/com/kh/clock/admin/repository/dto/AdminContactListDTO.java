package com.kh.clock.admin.repository.dto;

import com.kh.clock.member.domain.AdminVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminContactListDTO {
  private int adminSq;
  private String keyword;
}
