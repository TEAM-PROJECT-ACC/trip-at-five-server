package com.kh.clock.room.service;

import java.util.List;
import java.util.Map;
import com.kh.clock.common.file.dto.ImageFileDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;

public interface RoomImageService {

  /**
   * 객실 이미지 저장
   * 
   * @param roomimageDTO : 객실이미지 정보
   * @return
   */
  int insertRoomImage(RoomImageDTO roomimageDTO);
  
  /**
   * 특정 객실 이미지 목록 조회
   * @param roomSq : 객실번호
   * @return
   */
  List<RoomImageDTO> findRoomImageByRoomSq(int roomSq);
  
  /**
   * 객실 이미지 수정
   * @param roomImageDTO 객실이미지 정보
   * @return
   */
  int updateRoomImage(RoomImageDTO roomImageDTO);

  /**
   * 객실 이미지 삭제
   * @param imageList 
   */
  int deleteRoomImageByRoomSq(List<ImageFileDTO> imageList);
}
