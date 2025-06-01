package com.kh.clock.room.service;

import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.RoomDTO;


public interface RoomSerivce {
  /**
   * 객실 저장
   * @param room : 객실 정보
   * @param mp : 이미지목록
   * @return 1 성공, 0 실패
   */
  int insertRoom(RoomVO room, MultipartFile[] images);
  
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
  
  /**
   * 숙박업소번호로 객실번호 찾기
   * @param accomNo : 숙박업소 번호
   * @return
   */
  int findByRoomSq(int accomNo);
}
