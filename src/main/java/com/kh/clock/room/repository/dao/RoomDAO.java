package com.kh.clock.room.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.GetRoomDTO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;

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

  public List<RoomListDTO> selectAllList(int accomNo) {
    return sqlSession.selectList("roomMapper.selectAllList", accomNo);
  }

  public RoomDetailDTO findRoomByAccomNoAndRoomSq(GetRoomDTO getRoomDTO) {
    return sqlSession.selectOne("roomMapper.findRoomByAccomNoAndRoomSq", getRoomDTO);
  }
}
