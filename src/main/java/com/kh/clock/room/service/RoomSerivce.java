package com.kh.clock.room.service;

import com.kh.clock.room.repository.RoomDTO;
import org.springframework.web.multipart.MultipartFile;


public interface RoomSerivce {
  /**
   * 객실 저장
   * @param room : 객실 정보
   * @param mp : 이미지목록
   * @return 1 성송, 0 실패
   */
  int insertRoom(RoomDTO room, MultipartFile[] mp);
  
  /**
   * 객실 업데이트
   * @param room : 객실 정보
   * @return 1 성공, 0 실패
   */
  int updateRoom(RoomDTO room);
  
  /**
   * 객실 삭제
   * @param roomSq 해당 객실 키 값
   * @return 1 성공, 0 실패
   */
  int deleteRoom(int roomSq);
}
