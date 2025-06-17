package com.kh.clock.nonMember.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.nonMember.domain.NonMemberAccomInfo;
import com.kh.clock.nonMember.domain.NonMemberReserveItemVO;
import com.kh.clock.nonMember.domain.NonMemberRoomInfo;

@Repository
public class NonMemberDAO {
  private SqlSession sqlSession;
  
  public NonMemberDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  
  public NonMemberReserveItemVO selectReservation(String resCd) {
    return sqlSession.selectOne("nonMemberMapper.selectReservation", resCd);
  }


  public NonMemberRoomInfo selectReserveRoomInfo(int roomSq) {
    return sqlSession.selectOne("nonMemberMapper.selectReserveRoomInfo", roomSq);
  }


  public NonMemberAccomInfo selectReserveAccomInfo(int accomNo) {
    return sqlSession.selectOne("nonMemberMapper.selectReserveAccomInfo", accomNo);
  }

}
