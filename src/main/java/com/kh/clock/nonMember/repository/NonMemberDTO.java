package com.kh.clock.nonMember.repository;

import com.kh.clock.nonMember.domain.NonMemberAccomInfo;
import com.kh.clock.nonMember.domain.NonMemberReserveItemVO;
import com.kh.clock.nonMember.domain.NonMemberRoomInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonMemberDTO {
  private NonMemberReserveItemVO item;
  private NonMemberRoomInfo roomInfo;
  private NonMemberAccomInfo accomInfo;
}
