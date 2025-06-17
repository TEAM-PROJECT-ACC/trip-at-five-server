package com.kh.clock.admin.service;

import java.util.List;
import com.kh.clock.admin.repository.dto.AdminReservationCancelListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationDetailDTO;
import com.kh.clock.admin.repository.dto.AdminReservationListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationSearchDTO;
import com.kh.clock.admin.repository.dto.ReservationCancelDetailDTO;

public interface AdminReservationService {

  int selectTotalCount();

  List<AdminReservationListDTO> selectReservationList(AdminReservationSearchDTO adminResSeoDTO);

  AdminReservationDetailDTO findReservationByResCd(String resCd);

  int selectCancelTotalCount();

  List<AdminReservationCancelListDTO> selectReservationCancelList(AdminReservationSearchDTO adminReservationSearchDTO);

  ReservationCancelDetailDTO findReservationCancelByResCd(String resCd);

}
