package com.kh.clock.accommodation.repository.dto;

import lombok.Data;

@Data
public class AccomListInfoDTO {
  private String keyword;
  private String checkIn;
  private String checkOut;
  private int guests;
  private int page;
  private int size;
}
