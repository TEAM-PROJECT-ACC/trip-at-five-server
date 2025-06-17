package com.kh.clock.reservation.repository.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResUserInfo {
  private String resName;
  private String resPhone;
  private List<ResInfo> roomList;
}
