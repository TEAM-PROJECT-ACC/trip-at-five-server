package com.kh.clock.accommodation.repository.dto;

import java.util.List;
import com.kh.clock.review.repository.dto.ReviewDTO;
import com.kh.clock.room.domain.RoomVO;
import lombok.Data;

@Data
public class AccomDTO {
  private int accomSq;
  private String accomName;
  private String accomDesc;
  private double accomLon;
  private double accomLat;
  private String accomZipCode;
  private String accomAddr;
  private String accomPhone;
  private String pubFacInfo;
  private String inRoomFacInfo;
  private String etcFacInfo;
  private int accomTypeNo;
  private int locId;
  private int roomPrice;
  private String roomChkIn;
  private String roomChkOut;
  private List<RoomVO> roomList;
  
  private String accomTypeName;
  private String resCd;
  private List<ReviewDTO> reviewList;
  
  private int reviewCount;
  private String accomImgPathName;
  
  private List<AccomAdminImageDTO> images; 
  private List<String> roomImageList;
}
