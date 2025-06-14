package com.kh.clock.admin.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.admin.repository.dao.AdminReservationDAO;
import com.kh.clock.admin.repository.dto.AdminReservationListDTO;
import com.kh.clock.admin.service.AdminReservationService;
import com.kh.clock.common.pageInfo.PageInfo;

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
  public List<AdminReservationListDTO> selectReservationList(PageInfo pageInfo) {
    return reservationDAO.selectReservationList(pageInfo.getRowBounds());
  }

}
