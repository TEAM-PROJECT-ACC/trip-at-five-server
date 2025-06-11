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
    // TODO Auto-generated method stub
    return null;
  }


}
