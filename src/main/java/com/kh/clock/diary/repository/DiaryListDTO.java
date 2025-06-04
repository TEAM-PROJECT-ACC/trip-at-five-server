package com.kh.clock.diary.repository;

import java.util.ArrayList;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiaryListDTO {
  private ArrayList<DiaryVO> diaryList;// DiaryList
  private PageInfo pageInfo;
}
