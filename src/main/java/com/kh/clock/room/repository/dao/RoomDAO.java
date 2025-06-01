package com.kh.clock.room.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.room.domain.RoomVO;

@Repository
public class RoomDAO {
  private final SqlSession sqlSession;
  
  public RoomDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  // 객실 등록
  public int insertRoom(RoomVO room) {
    return sqlSession.insert("roomMapper.insertRoom", room);
  }

  // 숙박업소 아이디로 객실찾기
  public int findByAccomNo(int accomNo) {
    return sqlSession.selectOne("roomMapper.findByAccomNo", accomNo);
  }
}
