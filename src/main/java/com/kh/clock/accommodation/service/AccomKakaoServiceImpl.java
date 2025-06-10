package com.kh.clock.accommodation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.kh.clock.accommodation.repository.AccomDAO;
import com.kh.clock.accommodation.repository.dto.AccomKakaoDTO;
import com.kh.clock.accommodation.repository.dto.AccomKakaoResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccomKakaoServiceImpl implements AccomKakaoService{

  private final AccomDAO accomDAO;
  
  @Override
  public AccomKakaoResponseDTO getAccomKakao() {
    List<AccomKakaoDTO> accomKakao = accomDAO.selectAccomKakao();
    // LOC_ID로 그룹화함
    Map<Integer, List<AccomKakaoDTO>> groupByLocation = new HashMap<>();
    
    for(AccomKakaoDTO accom: accomKakao) {
      groupByLocation.computeIfAbsent(accom.getLocId(), k -> new ArrayList<>()).add(accom);
    }
    List<Double[]> regionCenters = 
    return null;
  }

}
