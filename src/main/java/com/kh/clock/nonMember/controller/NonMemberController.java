package com.kh.clock.nonMember.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.nonMember.domain.NonMemberAccomInfo;
import com.kh.clock.nonMember.domain.NonMemberReserveItemVO;
import com.kh.clock.nonMember.domain.NonMemberRoomInfo;
import com.kh.clock.nonMember.repository.NonMemberDTO;
import com.kh.clock.nonMember.service.NonMemberService;

@RestController
@RequestMapping("/non-members")
public class NonMemberController {
  private NonMemberService nonMemberService;
  
  public NonMemberController(NonMemberService nonMemberService) {
    this.nonMemberService = nonMemberService;
  }
  
  @GetMapping
  public NonMemberDTO selectReservation(@RequestParam String resCd) {
    NonMemberReserveItemVO item = nonMemberService.selectReservation(resCd);
    System.out.println(item);
    if(item != null) {
      NonMemberDTO responseData = new NonMemberDTO();
      int roomSq = item.getRoomNo();
      System.out.println(roomSq);
      NonMemberRoomInfo roomInfo = nonMemberService.selectReserveRoomInfo(roomSq);
      int accomNo = roomInfo.getAccomNo();
      NonMemberAccomInfo accomInfo = nonMemberService.selectReserveAccomInfo(accomNo);
      responseData.setItem(item);
      responseData.setRoomInfo(roomInfo);
      responseData.setAccomInfo(accomInfo);
      
      return responseData;
    } else {
      return null;
    }
  }
}
