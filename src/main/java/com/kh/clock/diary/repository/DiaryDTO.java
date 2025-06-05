package com.kh.clock.diary.repository;

import com.kh.clock.diary.domain.DiaryVO;
import lombok.Data;

@Data
public class DiaryDTO {
  private DiaryVO diary;
  private int pageNo;
  private int numOfRows;
}
