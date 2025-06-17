package com.kh.clock.room.repository.dto;

import com.kh.clock.common.pageInfo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchDTO {
  private PageInfo pageInfo;
  private int accomNo;
  private String keyword;
}
