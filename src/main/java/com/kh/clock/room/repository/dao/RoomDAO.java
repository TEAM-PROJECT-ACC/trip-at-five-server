package com.kh.clock.room.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.room.domain.Room;

@Repository
public class RoomDAO {
  private final SqlSession sqlSession;
  
  public RoomDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  // 객실 등록
  public int insertRoom(Room room) {
    return 0;
  }
}
