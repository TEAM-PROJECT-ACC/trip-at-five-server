package com.kh.clock.accommodation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.accommodation.repository.AccomDAO;
import com.kh.clock.accommodation.repository.AccomDTO;
import com.kh.clock.accommodation.repository.AccomListInfoDTO;

@Service
public class AccomServiceImpl implements AccomService {
  
  private final AccomDAO accomDAO;

  public AccomServiceImpl(AccomDAO accomDAO) {
      this.accomDAO = accomDAO;
  }

  @Override
  public List<AccomDTO> selectAll() {
      return accomDAO.selectAll();
  }

  @Override
  public AccomDTO getAccommodationById(int accomSq) {
      return accomDAO.selectAccomDetail(accomSq);
  }

  @Override
  public int createAccom(AccomDTO accom) {
      return accomDAO.insertAccom(accom);
  }

  @Override
  public int updateAccom(AccomDTO accom) {
      return accomDAO.updateAccom(accom);
  }

  @Override
  public int deleteAccom(int accomSq) {
      return accomDAO.deleteAccom(accomSq);
  }

  @Override
  public List<AccomDTO> searchAccom(AccomListInfoDTO searchFilter) {
    return accomDAO.searchAccom(searchFilter);
  }
}
