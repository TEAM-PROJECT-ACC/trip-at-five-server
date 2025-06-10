package com.kh.clock.accommodation.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccomKakaoResponseDTO {
  private double centerLat;
  private double centerLon;
  private int mapLevel;
}
