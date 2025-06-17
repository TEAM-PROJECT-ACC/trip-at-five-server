package com.kh.clock.room.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.common.file.dto.ImageFileDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;

@Repository
public class RoomImageDAO {
  private final SqlSession sqlSession;
  
  public RoomImageDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  public int insertRoomImage(RoomImageDTO roomImageDTO) {
    System.out.println("roomImageDTO : " + roomImageDTO);
    return sqlSession.insert("roomImageMapper.insertRoomImage", roomImageDTO);
  }

  public List<RoomImageDTO> findRoomImageByRoomSq(int roomNo) {
    return sqlSession.selectList("roomImageMapper.findRoomImageByRoomSq", roomNo);
  }

  public int updateRoomImage(RoomImageDTO roomImageDTO) {
    return sqlSession.update("roomImageMapper.updateRoomImage", roomImageDTO);
  }

  public int deleteRoomImageByRoomSq(ImageFileDTO imageFileDTO) {
    return sqlSession.delete("roomImageMapper.deleteRoomImageByRoomSq", imageFileDTO);
  }

  // 객실 이미지 경로 조회
  public String findRoomImageByRoomSqOne(int roomSq) {
    return sqlSession.selectOne("roomImageMapper.findRoomImageByRoomSqOne", roomSq);
  }
}
