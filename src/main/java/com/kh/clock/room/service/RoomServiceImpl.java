package com.kh.clock.room.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dao.RoomDAO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;

@Service
public class RoomServiceImpl implements RoomSerivce {
  
  @Value("${file.dir}")
  private String staticFilePath;
  
  private RoomDAO roomDAO;
  private RoomImageServiceImpl roomImageService;
  private OclockFileUtils oFileUtils; // 해당 클래스에서 @Component를 작성했으므로 의존성 주입 가능! 
  
  public RoomServiceImpl(RoomDAO roomDAO, OclockFileUtils oFileUtils, RoomImageServiceImpl roomImageService) {
    this.roomDAO = roomDAO;
    this.oFileUtils = oFileUtils;
    this.roomImageService = roomImageService;
  }

  /**
   * 전체 목록 조회
   */
  @Override
  public List<RoomListDTO> selectAllList(int accomNo) {
    List<RoomListDTO> roomList = roomDAO.selectAllList(accomNo);
    
    return roomList != null ? roomList : null;
  }

  /**
   * 객실 정보 저장
   */
  @Override
  @Transactional
  public int insertRoom(RoomVO room, MultipartFile[] images) {
    int roomImageResult = 0;
    System.out.println(room);
    System.out.println(images);
    int insertResult = roomDAO.insertRoom(room);
    System.out.println("insertResult : " + insertResult);
    
    if(insertResult > 0) {
      /**
       * TODO: 객실은 저장이 됨. 하지만 가장 최근에 저장한 객실ID값으로 객실이미지를 저장해야하는 것을 해야함
       * => 트랜잭션 처리로 해결 완료
       */
      int roomNo = room.getRoomSq(); // 트랜잭션 처리로 인해 방금 INSERT 한 객실 번호 값 불러오기
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        // 객실 이미지 처리
        List<String> fileUrls = saveRoomImage(images);
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(images[i].getOriginalFilename(), fileUrls.get(i), roomNo));
        }
        
        // 전달 받은 파일의 갯수와 DB에서 INSERT 한 행의 갯수가 동일하면 저장 성공!
        if(roomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
        }
      }
    }
    
    return insertResult;
  }

  @Override
  @Transactional
  public int updateRoom(RoomVO room, MultipartFile[] images) {
    int roomImageResult = 0;
//  System.out.println(room);
//  System.out.println(images);
    int updateResult = roomDAO.updateRoom(room);
//  System.out.println(updateResult);
  
    if(updateResult > 0) {
      int roomNo = room.getRoomSq();
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        // 객실 이미지 처리
        List<String> fileUrls = saveRoomImage(images);
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.updateRoomImage(new RoomImageDTO(images[i].getOriginalFilename(), fileUrls.get(i), roomNo));
        }
        
        if(roomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
        }
      }
    }
  
    return updateResult;
  }
  
//파일 처리
  private List<String> saveRoomImage(MultipartFile[] mp) {
    String middlePath = UploadFileType.ROOM.getPath(); // 중간 폴더 경로
    String dateFolderPath = oFileUtils.createFilePath(middlePath);
    
    List<String> fileUrls = new ArrayList<>();
 
    for (MultipartFile file : mp) {
        String fileName = OclockFileUtils.changeFileName(file);
        
//        System.out.println("변환된 파일 명 : " + fileName);
        
        oFileUtils.saveFile(file, dateFolderPath, fileName, UploadFileType.ROOM.getPath());
        String fileUrl = staticFilePath + middlePath + "/" + dateFolderPath + "/" + fileName; // DB에 저장할 파일 경로
        
//        System.out.println("fileUrl : " + fileUrl);
        
        fileUrls.add(fileUrl); // DB에 저장할 값을 리스트에 담기
    }
    
    return fileUrls;
  }

  @Override
  @Transactional
  public int deleteRoomAndRoomImageByAccomNoAndRoomSq(RoomIdentifierDTO roomIdenDTO) {
    int deleteResult = roomDAO.deleteRoomAndRoomImageByAccomNoAndRoomSq(roomIdenDTO);
    System.out.println(deleteResult);
  
    if(deleteResult > 0) {
      int roomNo = roomIdenDTO.getRoomSq();
      
      /**
       * 1. 객실번호로 해당 객실 이미지 조회
       * 
       * 2. 객실 이미지 path 값으로 해당 경로에 파일이 있는 지 체크
       * 
       * 2-1. 체크 이후 파일 삭제
       * 
       * 3. 객실번호로 DB에서 데이터 삭제
       */
      
      List<RoomImageDTO> roomImagePathList = roomImageService.findRoomImageByRoomSq(roomNo);
      
      boolean flag = false;
      for(int i = 0; i < roomImagePathList.size(); i++) {
        flag = deleteFile(roomImagePathList.get(i).getRoomImgPathName()); // 파일 삭제 함수 호출
        if(!flag) {
          System.out.println(
              roomImagePathList.get(i).getRoomImgPathName()
              + " 경로에 있는 " + roomImagePathList.get(i).getRoomImgOrgName()
              + " 파일을 삭제하지 못했습니다.");
          break;
        }
      }
      
      if(flag) roomImageService.deleteRoomImageByRoomSq(roomNo);
    }
    
    return deleteResult;
  }
  private boolean deleteFile(String path) {
    File file = new File(path);
    
    if(file.exists()) {
      if(file.delete()) {
        System.out.println("파일 삭제");
        return true;
      } else {
        System.out.println("파일 삭제 실패");
      }
    }
    return false;
  }

  @Override
  public RoomDetailDTO findRoomByAccomNoAndRoomSq(RoomIdentifierDTO getRoomDTO) {
    return roomDAO.findRoomByAccomNoAndRoomSq(getRoomDTO);
  }

  

}
