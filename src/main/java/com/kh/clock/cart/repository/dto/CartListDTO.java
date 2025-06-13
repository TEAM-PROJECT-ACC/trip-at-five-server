package com.kh.clock.cart.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListDTO {
  private String accomName;
  private String roomName;
  private int roomPrice;
  private int roomNo;
  private int accomNo;
}
