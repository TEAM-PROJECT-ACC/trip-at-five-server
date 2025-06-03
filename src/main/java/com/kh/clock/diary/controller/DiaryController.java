package com.kh.clock.diary.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.kh.clock.common.gson.CommonGson;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;
import com.kh.clock.diary.repository.DiaryDTO;
import com.kh.clock.diary.service.DiaryServiceImpl;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
  private final DiaryServiceImpl diaryService;
  
  public DiaryController(DiaryServiceImpl diaryService) {
    this.diaryService = diaryService;
  }
  
  /**
   * 나의 일지 전체 수 조회
   * [GET] selectTotalCount
   * @param memNo
   * @return int count
   */
  @GetMapping("select/all/count")
  public int selectTotalCount(@RequestParam int memNo) {
    int totalCount = diaryService.selectTotalCount(memNo);
    return totalCount;
  }
  
  /**
   * 나의 일지 전체 조회
   * [GET] selectAllList
   * @param int memNo 일지를 조회 할 회원 번호 
   * @param int pageNo 현재 페이지 번호
   * @param int numOfRows 페이지 당 표시 아이템 수
   * @return String responseData(ArrayList<DiaryVO> diaryList, PageInfo pageInfo)
   */
  @GetMapping("select/all")
  public String selectAllList(@RequestParam int memNo, @RequestParam int pageNo, @RequestParam int numOfRows) {
    int totalCount = selectTotalCount(memNo);
    PageInfo pageInfo = new PageInfo(totalCount, pageNo, numOfRows);
    Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
    ArrayList<DiaryVO> diaryList = diaryService.selectAllList(memNo, pageInfo);
    String responseData = gson.toJson(diaryList);

    return responseData;
  }
  
  /**
   * 나의 일지 조회
   * [GET] selectDiary
   * @param int memNo 일지를 조회 할 회원 번호
   * @param int diarySq 조회 할 일지 번호
   * @return String responseData(DiaryVO)
   */
  @GetMapping("select/diary")
  public DiaryVO selectDiary(@RequestParam int memNo, @RequestParam int diarySq) {
    DiaryVO diaryDTO = new DiaryVO();
    diaryDTO.setDiarySq(diarySq);
    diaryDTO.setMemNo(memNo);
    
    DiaryVO diary = diaryService.selectDiary(diaryDTO);
    
    return diary;
  }
  
  
  /**
   * 나의 일지 수정 
   * [PUT] modifyDiary
   * @param modifiedDiary 수정된 일지 객체
   * @return String responseData(DiaryVO) 수정 후 새로 조회한 일지 데이터
   */
  @PutMapping("modify/diary")
  public String modifyDiary(@RequestBody DiaryVO modifiedDiary) {
    Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
    int result = diaryService.updateDiary(modifiedDiary);
    if(result > 0) {
      DiaryVO diary = selectDiary(modifiedDiary.getMemNo(), modifiedDiary.getDiarySq());
      String responseData = gson.toJson(diary);
      return responseData;
    } else {
      return ""; // TODO: error 처리
    }
  }
  
  /**
   * @param memNo
   * @param diarySq
   * @param pageNo
   * @param numOfRows
   * @return Stirng responseData(ArrayList<DiaryVO>) 삭제 후 새로 조회한 일지 목록
   */
  @DeleteMapping("delete/diary")
  public String deleteDiary(
      @RequestParam int memNo, @RequestParam int diarySq, 
      @RequestParam int pageNo, @RequestParam int numOfRows
  ) {
    Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
    DiaryVO diaryDTO = new DiaryVO();
    diaryDTO.setDiarySq(diarySq);
    diaryDTO.setMemNo(memNo);

    int result = diaryService.deleteDiary(diaryDTO);

    if(result > 0) {
      int totalCount = selectTotalCount(memNo);
      PageInfo pageInfo = new PageInfo(totalCount, pageNo, numOfRows);
      ArrayList<DiaryVO> diaryList = diaryService.selectAllList(memNo, pageInfo);
      String responseData = gson.toJson(diaryList);
      return responseData;
    } else {
      return "";      
    }
  }
  
  @PostMapping("insert/diary")
  public String insertDiary(@RequestBody DiaryDTO diaryDTO) {
    Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
    DiaryVO diary = diaryDTO.getDiary();
    System.out.println(diary);
    
    int result = diaryService.insertDiary(diary);
    if(result > 0) {
      int memNo = diary.getMemNo();
      int totalCount = selectTotalCount(memNo);
      int pageNo = diaryDTO.getPageNo();
      int numOfRows = diaryDTO.getNumOfRows();
      
      PageInfo pageInfo = new PageInfo(totalCount, pageNo, numOfRows);
      ArrayList<DiaryVO> diaryList = diaryService.selectAllList(memNo, pageInfo);
      
      String responseData = gson.toJson(diaryList);
      return responseData;
    }
    return "";
  }
}
