package com.kh.clock.nonMember.service;

import com.kh.clock.nonMember.domain.NonMemberAccomInfo;
import com.kh.clock.nonMember.domain.NonMemberReserveItemVO;
import com.kh.clock.nonMember.domain.NonMemberRoomInfo;

public interface NonMemberService {

  /**
   * @param resCd
   * @return NonMemberReserveItemVO
   */
  public NonMemberReserveItemVO selectReservation(String resCd);

  /**
   * @param roomSq
   * @return NonMemberRoomInfo
   */
  public NonMemberRoomInfo selectReserveRoomInfo(int roomSq);

  /**
   * @param accomNo
   * @return NonMemberAccomInfo
   */
  public NonMemberAccomInfo selectReserveAccomInfo(int accomNo);

}
