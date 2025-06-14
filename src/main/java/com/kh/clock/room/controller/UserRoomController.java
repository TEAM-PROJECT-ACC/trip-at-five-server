package com.kh.clock.room.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  
  @GetMapping("/room-cnt")
  public ResponseEntity<Object> getRoomCnt(@RequestParam AvailableRoomRequestDTO availableRoomRequestDTO) {
    System.out.println("객실 수 조회를 위한 AvailableRoomRequestDTO : " + availableRoomRequestDTO);
    
    List<RoomCntDTO> roomCntList = roomService.selectRoomCnt(availableRoomRequestDTO);
    
    return ResponseEntity.status(HttpStatus.OK).body(roomCntList);
  }
}
