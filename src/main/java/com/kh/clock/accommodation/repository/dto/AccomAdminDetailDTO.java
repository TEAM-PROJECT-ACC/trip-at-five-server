package com.kh.clock.accommodation.repository.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class AccomAdminDetailDTO {
  private int accomSq;
  private String accomName;
  private String accomDesc;
  private double accomLon;
  private double accomLat;
  private String accomZipCode;
  private String accomAddr;
  private String accomPhone;
  private Date accomRegDt;
  private String pubFacInfo;
  private String inRoomFacInfo;
  private String etcFacInfo;
  private int accomTypeNo;
}
