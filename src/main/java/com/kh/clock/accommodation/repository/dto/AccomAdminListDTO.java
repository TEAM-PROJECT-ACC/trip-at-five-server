package com.kh.clock.accommodation.repository.dto;

import java.util.List;
import com.kh.clock.common.pageInfo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccomAdminListDTO {
  private List<AccommodationDTO> dataList;
  private PageInfo pageInfo;
}
