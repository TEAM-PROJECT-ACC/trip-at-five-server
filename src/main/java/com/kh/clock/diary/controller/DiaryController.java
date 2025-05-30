package com.kh.clock.diary.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/diaries")
public class DiaryController {
  // GET
  /**
   * @param Number memberNo(Required)
   * @return JsonArray<Diary> 
   */
  public String getDiaryList(@RequestParam int memberNo) {
    
    
    return "";
  }
}
