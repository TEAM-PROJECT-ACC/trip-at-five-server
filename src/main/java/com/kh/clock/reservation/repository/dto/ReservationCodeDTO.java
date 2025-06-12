package com.kh.clock.reservation.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCodeDTO {
  private String resEmail;
  private String resName;
  private String resPhone;
}
