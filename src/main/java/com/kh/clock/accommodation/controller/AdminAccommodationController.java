package com.kh.clock.accommodation.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.accommodation.repository.dto.AccomAdminDetailDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminImageDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminListDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminSearchDTO;
import com.kh.clock.accommodation.service.AccomImageServiceImpl;
import com.kh.clock.accommodation.service.AccomService;
import com.kh.clock.common.file.dto.ImageFileDTO;
// 관리자 페이지 기능
@RestController
@RequestMapping("/admin/accommodations")
public class AdminAccommodationController {
  
  private AccomService accomService;
  private AccomImageServiceImpl accomImageService;
  
  public AdminAccommodationController(AccomService accomService, AccomImageServiceImpl accomImageService) {
    this.accomService=accomService;
    this.accomImageService = accomImageService;
  }

  //숙박 업체 정보 목록 조회
  @GetMapping
  public List<AccomAdminListDTO> selectAdminAccomList(@ModelAttribute AccomAdminSearchDTO accomSearchDTO) {
    return accomService.selectAdminAccomList(accomSearchDTO);
  }
  
  // 숙박 업체 정보 상세 페이지(숙박업체 번호)
  @GetMapping("/{accomSq}/edit")
  public ResponseEntity<AccomAdminDetailDTO> selectAdminAccomDetailByAccomSq(@PathVariable int accomSq){
    AccomAdminDetailDTO accomDetail = accomService.selectAdminAccomDetailByAccomSq(accomSq);
    List<AccomAdminImageDTO> accomImageList = accomImageService.findAccomImageByAccomSq(accomSq);
    
    
    if (accomDetail == null) return ResponseEntity.notFound().build();
    
    List<ImageFileDTO> imageNameList = new ArrayList<>();
    for(int i = 0; i < accomImageList.size(); i++) {
      imageNameList.add(new ImageFileDTO(accomImageList.get(i).getAccomNo(), accomImageList.get(i).getAccomImgHashCd(), accomImageList.get(i).getAccomImgPathName()));
    }
    
    return ResponseEntity.ok(accomDetail);
  }
  
  // 숙박 업체 정보 상세 페이지 (수정)
  @PutMapping("/{accomSq}/edit")
  public ResponseEntity<String> updateAccommodation(
      @RequestBody AccomAdminDetailDTO updatedAccomInfo,
      @RequestPart(value = "images", required = false) MultipartFile[] images) {
    int result = accomService.updateAdminAccomDetail(updatedAccomInfo, null);
    if (result > 0) {
      return ResponseEntity.ok("수정 성공");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패");
    }
  }
  
  // 숙박 업체 정보 상세 페이지 (삭제)
  @DeleteMapping("/{accomSq}")
  public ResponseEntity<String> deleteAdminAccom(@PathVariable int accomSq) {
    int result = accomService.deleteAdminAccom(accomSq);
    if (result > 0) {
      return ResponseEntity.ok("삭제 완료");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 숙박 정보가 없습니다.");
    }
  }
  
  // 숙박 업체 정보 (등록)
  @PostMapping("/new")
  public ResponseEntity<Object> insertAdminAccom(@ModelAttribute AccomAdminDetailDTO accomDto, 
                  @RequestPart(value = "images", required = false) MultipartFile[] images) {
    if(images != null) {
      for(MultipartFile mf : images) System.out.println(mf.getOriginalFilename());
    }
    int result = accomService.insertAdminAccom(accomDto, images);
    if (result > 0) {
      return ResponseEntity.ok("등록 완료");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록 실패");
    }
  }
}
