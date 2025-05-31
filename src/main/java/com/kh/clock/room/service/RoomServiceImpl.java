package com.kh.clock.room.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.room.repository.RoomDTO;
import com.kh.clock.room.repository.dao.RoomDAO;

@Service
public class RoomServiceImpl implements RoomSerivce {
  
  private RoomDAO roomDAO;
  private OclockFileUtils oFileUtils; // 해당 클래스에서 @Component를 작성했으므로 의존성 주입 가능! 
  
  public RoomServiceImpl(RoomDAO roomDAO, OclockFileUtils oFileUtils) {
    this.roomDAO = roomDAO;
    this.oFileUtils = oFileUtils;
  }

  /**
   * 객실 정보 저장
   */
  @Override
  public int insertRoom(RoomDTO room, MultipartFile[] mp) { 
    // 파일 처리
    String middlePath = UploadFileType.ROOM.getPath(); // 중간 폴더 경로
    String dateFolderPath = oFileUtils.createFilePath(middlePath);
    
    List<String> fileUrls = new ArrayList<>();

    for (MultipartFile file : mp) {
        String fileName = OclockFileUtils.changeFileName(file);
        
        System.out.println("변환된 파일 명 : " + fileName);
        
        oFileUtils.saveFile(file, dateFolderPath, fileName, "acc/room");
        String fileUrl = "assets/resources/uploads/" + middlePath + "/" + dateFolderPath + "/" + fileName; // DB에 저장할 파일 경로
        
        System.out.println("fileUrl : " + fileUrl);
        
        fileUrls.add(fileUrl);
    }
    
    return 0;
  }

  @Override
  public int updateRoom(RoomDTO room) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int deleteRoom(int roomSq) {
    // TODO Auto-generated method stub
    return 0;
  }

}
