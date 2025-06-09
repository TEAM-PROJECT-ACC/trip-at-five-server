package com.kh.clock.accommodation.repository.dto;

import lombok.Data;

@Data
public class AccomAdminListDTO {
  private int accomSq;
  private String accomName;
  private String accomAddr;
  private String accomPhone;
  private int roomPrice;
  private String accomTypeName;
}
