package com.kh.clock.room.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.common.file.dto.DeleteImageRequestDTO;
import com.kh.clock.common.file.dto.ImageFileDTO;
import com.kh.clock.room.service.RoomImageServiceImpl;

@RestController
@RequestMapping("/admin/accommodations/{accomNo}/rooms/{roomNo}")
public class RoomImageController {

  private RoomImageServiceImpl roomImageService;
  
  public RoomImageController(RoomImageServiceImpl roomImageService) {
    this.roomImageService = roomImageService;
  }
  
  @DeleteMapping("/images")
  public ResponseEntity<Object> deleteImages(@PathVariable(value="accomNo") int accomNo, @PathVariable(value="roomNo") int roomNo, @RequestBody DeleteImageRequestDTO deleteImageReqDTO) {
//    System.out.println("객실 이미지 삭제 숙박업소 번호 : " + accomNo);
//    System.out.println("객실 이미지 삭제 객실 번호 : " + roomNo);

    List<ImageFileDTO> imageList = deleteImageReqDTO.getImageList();

//    imageList.forEach(image -> {
//      System.out.println("이미지 번호: " + image.getNo());
//      System.out.println("해시코드: " + image.getHashCode());
//      System.out.println("이미지 경로: " + image.getImagePath());
//    });
    
    int result = roomImageService.deleteRoomImageByRoomSq(imageList);

    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(result);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 이미지 삭제에 실패했습니다.");
    
  }
}
