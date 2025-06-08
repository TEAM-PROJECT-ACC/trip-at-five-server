package com.kh.clock.room.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;
import com.kh.clock.room.repository.dto.RoomSearchDTO;

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

  public List<RoomListDTO> selectAllList(RoomSearchDTO roomSearchDTO) {
    return sqlSession.selectList("roomMapper.selectAllList", roomSearchDTO);
  }

  // 객실 상세 조회
  public RoomDetailDTO findRoomByAccomNoAndRoomSq(RoomIdentifierDTO getRoomDTO) {
    return sqlSession.selectOne("roomMapper.findRoomByAccomNoAndRoomSq", getRoomDTO);
  }

  // 객실 수정
  public int updateRoom(RoomVO room) {
    return sqlSession.update("roomMapper.updateRoom", room);
  }

  // 객실 삭제
  public int deleteRoomAndRoomImageByAccomNoAndRoomSq(RoomIdentifierDTO roomIdenDTO) {
    System.out.println(roomIdenDTO);
    return sqlSession.delete("roomMapper.deleteRoomAndRoomImageByAccomNoAndRoomSq", roomIdenDTO);
  }
}
