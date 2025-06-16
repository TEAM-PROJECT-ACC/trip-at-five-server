package com.kh.clock.room.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.AvailableRoomRequestDTO;
import com.kh.clock.room.repository.dto.RoomCntDTO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;
import com.kh.clock.room.repository.dto.RoomSearchDTO;


public interface RoomService {
  
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
  int updateRoom(RoomVO roomVo, MultipartFile[] images);
  
  /**
   * 객실 삭제
   * @param accomNo 해당 숙박 키 값
   * @param roomNo 해당 객실 키 값
   * @return 1 성공, 0 실패
   */
  int deleteRoomAndRoomImageByAccomNoAndRoomSq(RoomIdentifierDTO roomIdenDTO);
  
  /**
   * 숙박업소번호로 객실번호 찾기
   * @param accomNo : 숙박업소 번호
   * @return
   */
  RoomDetailDTO findRoomByAccomNoAndRoomSq(RoomIdentifierDTO getRoomDTO);

  int selectTotalCount(int accomNo);

  /**
   * 전체 목록 조회
   */
  List<RoomListDTO> selectRoomList(RoomSearchDTO roomSearchDTO);

  List<RoomCntDTO> selectRoomCnt(AvailableRoomRequestDTO availableRoomRequestDTO);

}
