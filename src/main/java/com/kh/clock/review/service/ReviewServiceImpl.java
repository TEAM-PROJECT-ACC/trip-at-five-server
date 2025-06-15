package com.kh.clock.review.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.repository.dao.ReviewDAO;
import com.kh.clock.review.repository.dao.ReviewImageDAO;
import com.kh.clock.review.repository.dto.ReviewDTO;
import com.kh.clock.review.repository.dto.ReviewImageDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
  
  private final ReviewDAO reviewDAO;
  private final ReviewImageDAO reviewImageDAO;
  private final ReviewImageServiceImpl reviewImageService;
  private final OclockFileUtils oFileUtils;

  /**
   * 리뷰 정보 저장
   */
  @Override
  @Transactional
  public int insertReview(ReviewVO review, MultipartFile[] images) {
    int insertResult = reviewDAO.insertReview(review);

    int revNo = review.getRevSq();
    insertImageFun(insertResult, review.getRevSq(), images);

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

  @Override
  public List<ReviewDTO> selectReviewList(int accomNo) {
    List<ReviewDTO> reviewList = reviewDAO.selectReviewList(accomNo);
    for (ReviewDTO review : reviewList) {
        List<ReviewImageDTO> imageList = reviewImageDAO.selectReviewImageListByRevNo(review.getRevSq());
        review.setImageList(imageList);
    }
    return reviewList;
  }

  @Override
  public ReviewDTO selectLatestReview(int accomNo) {
    return reviewDAO.selectLatestReview(accomNo);
  }
  
  @Override
  public boolean existsValidReservation(String resCd, int accomSq) {
      Map<String, Object> param = new HashMap<>();
      param.put("resCd", resCd);
      param.put("accomSq", accomSq);
      return reviewDAO.countValidReservation(param) > 0;
  }
  @Override
  public boolean existsReviewByResCd(String resCd) {
      return reviewDAO.countReviewByResCd(resCd) > 0;
  }

  @Override
  public int selectReviewCount(int accomNo) {
    return reviewDAO.selectReviewCount(accomNo);
  }
  
  public double selectReviewAverageScore(int accomNo) {
    return reviewDAO.selectReviewAverageScore(accomNo);
  }

  @Override
  public String findResCode(int memNo, int accomSq) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Object> selectReviewList(int accomSq, int page, int size) {
    // TODO Auto-generated method stub
    return null;
  }

}
