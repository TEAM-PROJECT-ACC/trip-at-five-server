package com.kh.clock.room.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.room.repository.dto.AvailableRoomRequestDTO;
import com.kh.clock.room.repository.dto.RoomCntDTO;
import com.kh.clock.room.service.RoomServiceImpl;

@RestController
@RequestMapping("accommodations/{accomNo}/rooms")
public class UserRoomController {

  private final RoomServiceImpl roomService;
  
  public UserRoomController(RoomServiceImpl roomService) {
    this.roomService = roomService;
  }
  
  /**
   * 객실 수 조회
   * @param availableRoomRequestDTO
   * @return
   */
//  @GetMapping("/room-cnt")
//  public ResponseEntity<Object> getRoomCnt(@ModelAttribute AvailableRoomRequestDTO availableRoomRequestDTO) {
//      System.out.println("객실 수 조회 요청 DTO : " + availableRoomRequestDTO);
//      
//      List<RoomCntDTO> roomCntList = roomService.selectRoomCnt(availableRoomRequestDTO);
//      
//      System.out.println(roomCntList);
//
//      if(roomCntList != null) return ResponseEntity.status(HttpStatus.OK).body(roomCntList);
//      else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 수 조회에 실패했습니다.");
//  }

}
