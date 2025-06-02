package com.kh.clock.accommodation.service;

import java.util.List;
import com.kh.clock.accommodation.repository.AccomDTO;
import com.kh.clock.accommodation.repository.AccomListInfoDTO;

public interface AccomService {
  
  List<AccomDTO> selectAll();
  
  AccomDTO getAccommodationById(int accomSq);
  
  int createAccom(AccomDTO accom);
  
  int updateAccom(AccomDTO accom);
  
  int deleteAccom(int accomSq);

  List<AccomDTO> searchAccom(AccomListInfoDTO searchFilter);
}
