package com.kh.clock.room.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.service.RoomServiceImpl;

@RestController
@RequestMapping("/admin/accommodations/{accomNo}/rooms")
public class RoomController {
  
  private RoomServiceImpl roomService;
  
  public RoomController(RoomServiceImpl roomService) {
    this.roomService = roomService;
  }
  
  
  @GetMapping("")
  public String test() {
    return "test";
  }
  
  /**
   * 객실 등록
   * @param accomNo 숙박업소번호
   * @param room 등록할 객실 정보
   * @return 
   */
  @PostMapping("")
  public ResponseEntity<Object> insertRoom(
      @PathVariable("accomNo") int accomNo, 
      @ModelAttribute RoomVO roomVo, 
      @RequestPart("images") MultipartFile[] images) {
    // 데이터 확인하기
//    System.out.println(roomVo); 
//    if(images != null) {
//      for(MultipartFile mf : images) System.out.println(mf.getOriginalFilename());
//    }
    
    int result = roomService.insertRoom(roomVo, images);
    
    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(roomVo);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 등록에 실패했습니다.");
    
  }

  
}
