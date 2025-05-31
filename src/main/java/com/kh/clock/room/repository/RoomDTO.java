package com.kh.clock.room.repository;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDTO {
  /**
   * roomSq : 객실번호
   * roomName : 객실명
   * roomPrice : 객실가격
   * roomChkIn : 객실 체크인 시간
   * roomChkOut : 객실 체크아웃 시간
   * roomStdPpl : 객실 기준인원
   * roomMaxPpl : 객실 최대인원
   * roomCnt : 객실 수
   * accomNo : 숙박업소번호
   */
  private int roomSq;
  private String roomName;
  private int roomPrice;
  private String roomChkIn;
  private String roomChkOut;
  private int roomStdPpl;
  private int roomMaxPpl;
  private int roomCnt;
  private int accomNo;
}
