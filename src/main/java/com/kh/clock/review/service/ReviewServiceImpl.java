package com.kh.clock.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.repository.dao.ReviewDAO;
import com.kh.clock.review.repository.dto.ReviewImageDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;

@Service
public class ReviewServiceImpl implements ReviewService{
  
  private ReviewDAO reviewDAO;
  private ReviewImageServiceImpl reviewImageService;
  private OclockFileUtils oFileUtils;
  
  public ReviewServiceImpl(ReviewDAO reviewDAO, OclockFileUtils oFileUtils, ReviewImageServiceImpl reviewImageService) {
    this.reviewDAO = reviewDAO;
    this.oFileUtils = oFileUtils;
    this.reviewImageService = reviewImageService;
  }
  
  /**
   * 리뷰 정보 저장
   */
  @Override
  @Transactional
  public int insertReview(ReviewVO review, MultipartFile[] images) {
    int insertResult = reviewDAO.insertReview(review);
    
    if (insertResult > 0) {
      insertImageFun(insertResult, review.getRevSq(), images);
    }
    return insertResult;
  }

  private void insertImageFun(int judge, int typeNumKey, MultipartFile[] images) {
    int reviewImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    List<String> deleteList = new ArrayList<>();
    if(judge > 0) {
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        String typePath = UploadFileType.REIVEW.getPath();
        
        // hash 코드 구하기
        List<String> hashCodeList = oFileUtils.getHashCodeList(images, typePath);

        for(int i = 0; i < images.length; i++) {
          newImageList.add(images[i]);
          deleteList.add(hashCodeList.get(i));
        }
        
        // 객실 이미지 처리
        List<String> fileUrls = oFileUtils.saveImage(newImageList, typePath);    
        for(int i = 0; i < fileUrls.size(); i++) {
          reviewImageResult += reviewImageService.insertReviewImage(new ReviewImageDTO(hashCodeList.get(i), newImageList.get(i).getOriginalFilename(), fileUrls.get(i), typeNumKey));
        }
        // 전달 받은 파일의 갯수와 DB에서 INSERT 한 행의 갯수가 동일하면 저장 성공!
        if(reviewImageResult == fileUrls.size()) {
//          System.out.println("파일 데이터 저장 성공!");
          
          // 임시 폴더 내 파일 삭제
          oFileUtils.deleteTempFolder(newImageList, deleteList, hashCodeList, typePath);
        }
      }
    }
  }

  // 리뷰 목록 조회
  @Override
  public Map<String, Object> selectReviewList(int accomSq, int page, int size) {
    // TODO Auto-generated method stub
    return null;
  }

  // 예약코드
  @Override
  public String getResCode(int memNo, int accomSq) {
    return reviewDAO.findResCode(memNo, accomSq);
  }

}
