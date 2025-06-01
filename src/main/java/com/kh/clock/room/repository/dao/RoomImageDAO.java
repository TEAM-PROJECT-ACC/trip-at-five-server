package com.kh.clock.room.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.room.repository.dto.RoomImageDTO;

@Repository
public class RoomImageDAO {
  private final SqlSession sqlSession;
  
  public RoomImageDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  public int insertRoomImage(RoomImageDTO roomImageDTO) {
    return sqlSession.insert("roomImageMapper.insertRoomImage", roomImageDTO);
  }

}
