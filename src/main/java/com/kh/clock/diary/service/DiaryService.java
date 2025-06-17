package com.kh.clock.diary.service;

import java.util.ArrayList;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;

/* DiaryService interface */
public interface DiaryService {
  
  
  /**
   * @param int memNo
   * @return int totalCount
   */
  public int selectTotalCount(int memNo);
  /**
   * @param int memberNo 
   * @return ArrayList<DiaryVO>
   */
  public ArrayList<DiaryVO> selectAllList(int memberNo, PageInfo pageInfo);
  
  
  /**
   * @param memNo
   * @param diarySq
   * @return <DiaryVO>
   */
  public DiaryVO selectDiary(DiaryVO diaryDTO);
  
  /**
   * @param modifiedDiary
   * @return int result
   */
  public int updateDiary(DiaryVO modifiedDiary);
  
  /**
   * @param memNo
   * @param diarySq
   * @return int result
   */
  public int deleteDiary(DiaryVO diaryDTO);
 
  /**
   * @param diary
   * @return int result
   */
  public int insertDiary(DiaryVO diary);
}
