package com.kh.clock.order.repository.dto;

import java.util.List;
import com.kh.clock.payment.repository.dto.PayInfoDTO;
import com.kh.clock.reservation.repository.dto.ResUserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
  private ResUserInfoDTO resUserInfo;
  private List<OrderResInfoDTO> resInfo;
  private PayInfoDTO payInfo;
}
