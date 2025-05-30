package com.kh.clock.diary.service;

import java.util.ArrayList;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;

/* DiaryService interface */
public interface DiaryService {
  
  
  /**
   * @param int memNo
   * @return totalCount
   */
  public int selectTotalCount(int memNo);
  /**
   * @param int memberNo 
   * @return ArrayList<DiaryVO>
   */
  public ArrayList<DiaryVO> selectAllList(int memberNo, PageInfo pageInfo);
}
