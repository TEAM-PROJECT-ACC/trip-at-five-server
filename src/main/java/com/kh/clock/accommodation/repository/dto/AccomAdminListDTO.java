package com.kh.clock.accommodation.repository.dto;

import lombok.Data;

@Data
public class AccomAdminListDTO {
  private int no;
  private String accomTypeName;
  private String accomName;
  private String accomAddr;
  private String accomPhone;
  private int roomPrice;
}
