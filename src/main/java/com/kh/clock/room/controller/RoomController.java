package com.kh.clock.room.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;
import com.kh.clock.room.service.RoomImageServiceImpl;
import com.kh.clock.room.service.RoomServiceImpl;

@RestController
@RequestMapping("/admin/accommodations/{accomNo}/rooms")
public class RoomController {
  
  private RoomServiceImpl roomService;
  private RoomImageServiceImpl roomImageService;
  
  public RoomController(RoomServiceImpl roomService, RoomImageServiceImpl roomImageService) {
    this.roomService = roomService;
    this.roomImageService = roomImageService;
  }
  
  /**
   * 해당 숙박업소번호에 의한 객실 전체 조회
   * @param accomNo 숙박업소번호
   * @return
   */
  @GetMapping("")
  public ResponseEntity<Object> selectRoomList(@PathVariable("accomNo") int accomNo, @RequestParam(value="currentPage") int currentPage) {
//    System.out.println("GetMapping : " + accomNo);
//    System.out.println((currentPage != 0 ? currentPage : "null입니다."));
    List<RoomListDTO> roomList = roomService.selectAllList(accomNo);

    if(roomList != null) return ResponseEntity.status(HttpStatus.OK).body(roomList);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 전체 조회에 실패했습니다.");
  }
  
  /**
   * 객실 수정 페이지
   * @param accomNo : 숙박업소번호
   * @param roomSq : 객실번호
   * @return
   */
  @GetMapping("/{roomSq}")
  public ResponseEntity<Object> findRoomByRoomSq(@PathVariable("accomNo") int accomNo, @PathVariable("roomSq") int roomSq) {
    
//    System.out.println(accomNo);
//    System.out.println(roomSq);
    
    // 객실 정보 조회
    RoomDetailDTO roomDetailDTO = roomService.findRoomByAccomNoAndRoomSq(new RoomIdentifierDTO(accomNo, roomSq));
//    System.out.println(roomDetailDTO);
    
    // 객실 이미지 정보 조회
    List<RoomImageDTO> roomImageList = roomImageService.findRoomImageByRoomSq(roomSq);
    
//    for(RoomImageDTO ri : roomImageList) System.out.println("파일명 : " + ri); // 디버깅
    
    if(roomDetailDTO != null) {
      List<String> imageNameList = new ArrayList<>();
      for(int i = 0; i < roomImageList.size(); i++) {
        imageNameList.add(roomImageList.get(i).getRoomImgPathName());
      }
//      System.out.println(roomDetailDTO);
      roomDetailDTO.setImageList(imageNameList);
      return ResponseEntity.status(HttpStatus.OK).body(roomDetailDTO);
    } else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 조회에 실패했습니다.");
  }
  
  /**
   * 객실 등록
   * @param accomNo 숙박업소번호
   * @param room 등록할 객실 정보
   * @return 
   */
  @PostMapping("")
  public ResponseEntity<Object> insertRoom(
      @PathVariable("accomNo") int accomNo, 
      @ModelAttribute RoomVO roomVo, 
   // required = false 으로 해서 필수 항목에서 제외 => null 일 경우 계속 400에러 발생..
      @RequestPart(value = "images", required = false) MultipartFile[] images) {
    
    // 데이터 확인하기
//    System.out.println(roomVo); 
//    if(images != null) {
//      for(MultipartFile mf : images) System.out.println(mf.getOriginalFilename());
//    }
    
    int result = roomService.insertRoom(roomVo, images);
    
    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(roomVo);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 등록에 실패했습니다.");
    
  }

  /**
   * 객실 수정
   * @param accomNo 숙박업소번호
   * @param room 등록할 객실 정보
   * @return 
   */
  @PutMapping("")
  public ResponseEntity<Object> updateRoom(
      @PathVariable("accomNo") int accomNo, 
      @ModelAttribute RoomVO roomVo, 
   // required = false 으로 해서 필수 항목에서 제외 => null 일 경우 계속 400에러 발생..
      @RequestPart(value = "images", required = false) MultipartFile[] images) {
    
    // 데이터 확인하기
//    System.out.println(roomVo); 
//    if(images != null) {
//      for(MultipartFile mf : images) System.out.println(mf.getOriginalFilename());
//    }
//    
    int result = roomService.updateRoom(roomVo, images);
    
    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(roomVo);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 수정에 실패했습니다.");
    
  }
  
  /**
   * 객실 정보 삭제
   * @param accomNo
   * @param roomSq
   * @return
   */
  @DeleteMapping("")
  public ResponseEntity<Object> deleteRoom(@RequestBody Map<String, Object> requestData) {
//    System.out.println(requestData.get("accomId"));
//    System.out.println(requestData.get("roomId"));
    
    int result = roomService.deleteRoomAndRoomImageByAccomNoAndRoomSq(
        new RoomIdentifierDTO(
            Integer.parseInt(requestData.get("accomId").toString()), 
            Integer.parseInt(requestData.get("roomId").toString())));
    
    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(result);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 삭제에 실패했습니다.");
  }
  
}
