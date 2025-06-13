package com.kh.clock.reservation.service;

import java.util.List;
import com.kh.clock.reservation.repository.dto.ReservationCodeDTO;
import com.kh.clock.reservation.repository.dto.ReservationDTO;

public interface ReservationService {

  List<String> insertReservation(ReservationDTO reservationDTO, List<Integer> roomInfo);

}
