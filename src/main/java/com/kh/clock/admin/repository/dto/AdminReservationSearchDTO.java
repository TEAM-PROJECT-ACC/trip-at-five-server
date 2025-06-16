package com.kh.clock.admin.repository.dto;

import com.kh.clock.common.pageInfo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationSearchDTO {
  private PageInfo pageInfo;
  private String keyword;
}
