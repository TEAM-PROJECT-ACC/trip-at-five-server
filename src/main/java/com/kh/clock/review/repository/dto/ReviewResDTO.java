package com.kh.clock.review.repository.dto;

import com.kh.clock.reservation.repository.dto.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResDTO {
  private ReservationDTO reservation;
  private String roomName;
  private int accomNo;
}
