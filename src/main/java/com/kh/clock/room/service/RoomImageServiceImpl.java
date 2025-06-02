package com.kh.clock.room.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.clock.room.repository.dao.RoomImageDAO;
import com.kh.clock.room.repository.dto.RoomImageDTO;

@Service
public class RoomImageServiceImpl implements RoomImageService {
  private RoomImageDAO roomImageDAO;
  
  public RoomImageServiceImpl(RoomImageDAO roomImageDAO) {
    this.roomImageDAO = roomImageDAO;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED) // => 자식/부모에서 ROLLBACK 이 발생된다면 자식과 부모 모두 ROLLBACK 한다는 의미
  public int insertRoomImage(RoomImageDTO roomimageDTO) {
    return roomImageDAO.insertRoomImage(roomimageDTO);
  }

  @Override
  public List<RoomImageDTO> findRoomImageByRoomSq(int roomSq) {
    System.out.println("roomSq : " + roomSq);
    return roomImageDAO.findRoomImageByRoomSq(roomSq);
  }

}
