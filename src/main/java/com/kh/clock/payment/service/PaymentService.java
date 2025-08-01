package com.kh.clock.payment.service;

import com.kh.clock.payment.repository.dto.CancelDTO;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import com.kh.clock.payment.repository.dto.PayInfoDTO;

public interface PaymentService {

  int payConfirm(ConfirmDTO confirmDTO);

  int updatePayState(ConfirmDTO confirmDTO);

  int payCancel(CancelDTO cancelDTO);

}
