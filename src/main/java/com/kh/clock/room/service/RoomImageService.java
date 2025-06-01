package com.kh.clock.room.service;

import com.kh.clock.room.repository.dto.RoomImageDTO;

public interface RoomImageService {

  /**
   * 객실 이미지 저장
   * 
   * @param roomimageVO : 객실이미지 정보
   * @return
   */
  int insertRoomImage(RoomImageDTO roomimageDTO);
}
