package com.kh.clock.accommodation.repository;

import lombok.Data;

@Data

public class AccomCreateDTO {
  private String accomTypeName;
  private String accomName;
  private String accomPhone;
  private String accomImgOrgName;
  private String accomImgPathName;
  private String accomDesc;
  private String accomZipCode;
  private String accomAddr;
  private String pubFacInfo;
  private String inRoomFacInfo;
  private String etcFacInfo;
}
