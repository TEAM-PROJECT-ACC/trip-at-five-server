package com.kh.clock.room.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.dto.ImageFileDTO;
import com.kh.clock.room.repository.dao.RoomImageDAO;
import com.kh.clock.room.repository.dto.RoomImageDTO;

@Service
public class RoomImageServiceImpl implements RoomImageService {
  private RoomImageDAO roomImageDAO;
  OclockFileUtils oFileUtils;
  
  public RoomImageServiceImpl(RoomImageDAO roomImageDAO, OclockFileUtils oFileUtils) {
    this.roomImageDAO = roomImageDAO;
    this.oFileUtils = oFileUtils;
  }

  /**
   * 객실이미지 등록
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED) // => 자식/부모에서 ROLLBACK 이 발생된다면 자식과 부모 모두 ROLLBACK 한다는 의미
  public int insertRoomImage(RoomImageDTO roomimageDTO) {
    return roomImageDAO.insertRoomImage(roomimageDTO);
  }

  /**
   * 객실번호로 객실 이미지 조회
   */
  @Override
  public List<RoomImageDTO> findRoomImageByRoomSq(int roomSq) {
    return roomImageDAO.findRoomImageByRoomSq(roomSq);
  }

  /**
   * 객실 이미지 수정
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public int updateRoomImage(RoomImageDTO roomImageDTO) {
    return roomImageDAO.updateRoomImage(roomImageDTO);
  }

  /**
   * 객실 이미지 삭제
   * @param imageList 
   */
  @Override
  public int deleteRoomImageByRoomSq(List<ImageFileDTO> imageList) {
    int result = 0;
    for(int i = 0; i < imageList.size(); i++) {
      oFileUtils.deleteFile(imageList.get(i).getImagePath());
      
      result += roomImageDAO.deleteRoomImageByRoomSq(imageList.get(i));
    }
    
//    return 1;
    return result;
  }

}
