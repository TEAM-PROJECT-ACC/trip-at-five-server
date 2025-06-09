package com.kh.clock.reservation.service;

import com.kh.clock.reservation.repository.dto.ReservationCodeDTO;

public interface ReservationService {

  String createReservationCode(ReservationCodeDTO resCodeDTO);

}
