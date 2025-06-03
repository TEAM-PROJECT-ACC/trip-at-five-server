package com.kh.clock.diary.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;
import com.kh.clock.diary.repository.DiaryDAO;

@Service
public class DiaryServiceImpl implements DiaryService {
  // DiaryDAO 필드 생성해야 함
  private final DiaryDAO diaryDAO;
  
  public DiaryServiceImpl(DiaryDAO diaryDAO) {
    this.diaryDAO = diaryDAO;
  }
  
  /**
   * @param memNo
   * @return int totalCount
   */
  @Override
  public int selectTotalCount(int memNo) {
    int totalCount = diaryDAO.selectTotalCount(memNo);
    return totalCount;
  }
  
  /**
   * @param int memberNo 회원 번호
   * @param int pageNo 현재 페이지 번호
   * @param int numOfRows 페이지 당 표시 아이템 수
   * @return ArrayList<DiaryVO>
   */
  @Override
  public ArrayList<DiaryVO> selectAllList(int memNo, PageInfo pageInfo) {
    ArrayList<DiaryVO> diaryList = (ArrayList<DiaryVO>)diaryDAO.selectAllList(memNo, pageInfo);
    return diaryList;
  }
  
  /**
   * @param memNo 회원 번호
   * @param diarySq 일지 번호
   * @return <DiaryVO>
   */
  @Override
  public DiaryVO selectDiary(DiaryVO diaryDTO) {
    DiaryVO diary = diaryDAO.selectDiary(diaryDTO);
    
    return diary;
  }
  
  /**
   * @param modifiedDiary 수정된 일지 객체
   * @return int result
   */
  @Override
  public int updateDiary(DiaryVO modifiedDiary) {
    int result = diaryDAO.updateDiary(modifiedDiary);
    return result;
  }
  
  /**
   * @param memNo
   * @param diarySq
   * @return int result
   */
  @Override
  public int deleteDiary(DiaryVO diaryDTO) {
    int result = diaryDAO.deleteDiary(diaryDTO);
    return result;
  }
  
  /**
   * @param diary
   * @return int result
   */
  @Override
  public int insertDiary(DiaryVO diary) {
    int result = diaryDAO.insertDiary(diary);
    return result;
  }
}
