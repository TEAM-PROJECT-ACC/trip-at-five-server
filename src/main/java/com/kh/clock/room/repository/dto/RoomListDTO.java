package com.kh.clock.room.repository.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomListDTO {
  /**
   * roomSq: 객실번호
   * roomName: 객실명
   * roomCnt: 객실 수
   * roomPrice: 객실가격
   */
  private int no;
  private String roomName;
  private int roomCnt;
  private int roomPrice;
}
