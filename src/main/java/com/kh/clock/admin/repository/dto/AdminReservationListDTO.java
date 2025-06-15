package com.kh.clock.admin.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationListDTO {
  private String no;
  private String accomName;
  private String resName;
  private String resPhone;
  private String resEmailId;
  private String ckResSt;
}
