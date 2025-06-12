package com.kh.clock.accommodation.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccomKakaoDTO {
  private double accomLon;
  private double accomLat;
  private int locId;
}
