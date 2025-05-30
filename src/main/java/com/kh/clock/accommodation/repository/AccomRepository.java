package com.kh.clock.accommodation.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.clock.accommodation.domain.AccomVO;

@Mapper
public interface AccomRepository {
  
  // 숙박 목록 조회
  List<AccomVO>selectAll();
  
  // 숙박 등록
  void insertAccom(AccomVO accom);
  
  // 숙박 수정
  void updateAccom(AccomVO accom);
  
  // 숙박 삭제
  void deleteAccom(int accomSq);
  
}
