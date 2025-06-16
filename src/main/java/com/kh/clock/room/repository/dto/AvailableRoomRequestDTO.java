package com.kh.clock.room.repository.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRoomRequestDTO {
  private int accomNo;
  private Date checkInDt;
  private Date checkOutDt;
}
