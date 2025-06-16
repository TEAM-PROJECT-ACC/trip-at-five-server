package com.kh.clock.room.repository.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.AvailableRoomRequestDTO;
import com.kh.clock.room.repository.dto.RoomCntDTO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;
import com.kh.clock.room.repository.dto.RoomSearchDTO;

@Repository
public class RoomDAO {
  private final SqlSession sqlSession;
  
  public RoomDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  // 객실 등록
  public int insertRoom(RoomVO room) {
    return sqlSession.insert("roomMapper.insertRoom", room);
  }

  // 숙박업소 아이디로 객실찾기
  public int findByAccomNo(int accomNo) {
    return sqlSession.selectOne("roomMapper.findByAccomNo", accomNo);
  }

  public List<RoomListDTO> selectRoomList(RoomSearchDTO roomSearchDTO) {
    return sqlSession.selectList("roomMapper.selectRoomList", roomSearchDTO);
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

  // 객실 수 조회
  public List<RoomCntDTO> selectRoomCnt(AvailableRoomRequestDTO availableRoomRequestDTO) {
    System.out.println(availableRoomRequestDTO);
    return sqlSession.selectList("roomMapper.selectRoomCnt", availableRoomRequestDTO);
  }

  public int selectTotalCount(int accomNo) {
    return sqlSession.selectOne("roomMapper.selectTotalCount", accomNo);
  }
}
