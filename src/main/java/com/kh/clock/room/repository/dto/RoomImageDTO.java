package com.kh.clock.room.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 객실 이미지 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomImageDTO {
  /**
   * roomImgOrgName : 객실이미지원본명
   * roomImgPathName : 객실이미지변환명
   * roomNo : 객실번호
   */
  private String roomImgOrgName;
  private String roomImgPathName;
  private int roomNo;
}
