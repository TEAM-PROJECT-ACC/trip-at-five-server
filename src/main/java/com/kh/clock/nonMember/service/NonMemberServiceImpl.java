package com.kh.clock.nonMember.service;

import org.springframework.stereotype.Service;
import com.kh.clock.nonMember.domain.NonMemberAccomInfo;
import com.kh.clock.nonMember.domain.NonMemberReserveItemVO;
import com.kh.clock.nonMember.domain.NonMemberRoomInfo;
import com.kh.clock.nonMember.repository.NonMemberDAO;

@Service
public class NonMemberServiceImpl implements NonMemberService {
  private NonMemberDAO nonMemberDAO;
  
  public NonMemberServiceImpl(NonMemberDAO nonMemberDAO) {
    this.nonMemberDAO = nonMemberDAO;
  }
  @Override
  public NonMemberReserveItemVO selectReservation(String resCd) {
    NonMemberReserveItemVO item = nonMemberDAO.selectReservation(resCd);
    return item;
  }
  
  @Override
  public NonMemberRoomInfo selectReserveRoomInfo(int roomSq) {
    NonMemberRoomInfo roomInfo = nonMemberDAO.selectReserveRoomInfo(roomSq);
    return roomInfo;
  }
  @Override
  public NonMemberAccomInfo selectReserveAccomInfo(int accomNo) {
    NonMemberAccomInfo accomInfo = nonMemberDAO.selectReserveAccomInfo(accomNo);
    return accomInfo;
  }
}
