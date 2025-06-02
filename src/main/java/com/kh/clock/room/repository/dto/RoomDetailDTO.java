package com.kh.clock.room.repository.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoomDetailDTO {
  private int roomSq;
  private String roomName;
  private int roomPrice;
  private String roomChkIn;
  private String roomChkOut;
  private int roomStdPpl;
  private int roomMaxPpl;
  private int roomCnt;
  private List<String> imageList;
  private int accomNo;
}
