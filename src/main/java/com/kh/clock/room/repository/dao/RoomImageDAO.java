package com.kh.clock.room.repository.dao;

import java.util.List;
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

  public List<RoomImageDTO> findRoomImageByRoomSq(int roomNo) {
    return sqlSession.selectList("roomImageMapper.findRoomImageByRoomSq", roomNo);
  }

  public int updateRoomImage(RoomImageDTO roomImageDTO) {
    return sqlSession.update("roomImageMapper.updateRoomImage", roomImageDTO);
  }

  public int deleteRoomImageByRoomSq(int roomNo) {
    return sqlSession.delete("deleteRoomImageByRoomSq", roomNo);
  }

}
