package com.kh.clock.admin.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.admin.repository.dao.AdminReservationDAO;
import com.kh.clock.admin.repository.dto.AdminReservationCancelListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationDetailDTO;
import com.kh.clock.admin.repository.dto.AdminReservationListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationSearchDTO;
import com.kh.clock.admin.repository.dto.ReservationCancelDetailDTO;
import com.kh.clock.admin.service.AdminReservationService;

@Service
public class AdminReservationServiceImpl implements AdminReservationService {
  
  private final AdminReservationDAO reservationDAO;
  
  public AdminReservationServiceImpl(AdminReservationDAO reservationDAO) {
    this.reservationDAO = reservationDAO;
  }

  @Override
  public int selectTotalCount() {
    return reservationDAO.selectReservationCount();
  }

  @Override
  public List<AdminReservationListDTO> selectReservationList(AdminReservationSearchDTO adminResSeoDTO) {
    return reservationDAO.selectReservationList(adminResSeoDTO.getKeyword(), adminResSeoDTO.getPageInfo().getRowBounds());
  }

  @Override
  public AdminReservationDetailDTO findReservationByResCd(String resCd) {
    return reservationDAO.findReservationByResCd(resCd);
  }

  @Override
  public int selectCancelTotalCount() {
    return reservationDAO.selectCancelTotalCount();
  }

  @Override
  public List<AdminReservationCancelListDTO> selectReservationCancelList(AdminReservationSearchDTO adminReservationSearchDTO) {
    return reservationDAO.selectReservationCancelList(adminReservationSearchDTO.getKeyword(), adminReservationSearchDTO.getPageInfo().getRowBounds());
  }

  @Override
  public ReservationCancelDetailDTO findReservationCancelByResCd(String resCd) {
    return reservationDAO.findReservationCancelByResCd(resCd);
    }

}
