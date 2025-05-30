package com.kh.clock.diary.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;
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
   * [GET] selectAllDiaryList
   * @param int memberNo 일지를 조회 할 회원 번호 
   * @param int pageNo 현재 페이지 번호
   * @param int numOfRows 페이지 당 표시 아이템 수
   * @return String responseData(ArrayList<DiaryVO> diaryList, PageInfo pageInfo)
   */
  @GetMapping("select/all")
  public String selectAllList(@RequestParam int memNo, @RequestParam int pageNo, @RequestParam int numOfRows) {
    System.out.println("나의 일지 전체 조회, memberNo : " +  memNo + ", pageNo : " + pageNo);
    int totalCount = selectTotalCount(memNo);
    PageInfo pageInfo = new PageInfo(pageNo, numOfRows, totalCount);
    System.out.println("totalCount : " + totalCount);
    Gson gson = new Gson();
    ArrayList<DiaryVO> diaryList = diaryService.selectAllList(memNo, pageInfo);
    System.out.println("나의 일지 전체 조회, DiaryVOList : " + diaryList);
    String responseData = gson.toJson(diaryList);
    
    return ""; // responseData;
  }
}
