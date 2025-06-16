package com.kh.clock.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.admin.repository.dto.AdminReservationCancelListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationDetailDTO;
import com.kh.clock.admin.repository.dto.AdminReservationListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationSearchDTO;
import com.kh.clock.admin.repository.dto.ReservationCancelDetailDTO;
import com.kh.clock.admin.service.impl.AdminReservationServiceImpl;
import com.kh.clock.common.pageInfo.PageInfo;

@RestController
@RequestMapping("/admin/reservations")
public class AdminReservationController {
  
  private final AdminReservationServiceImpl adminReservationService;
  
  public AdminReservationController(AdminReservationServiceImpl adminReservationService) {
    this.adminReservationService = adminReservationService;
  }
  
  @GetMapping
  public ResponseEntity<Map<String, Object>> selectReservationList(
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "1") int currentPage,
      @RequestParam(defaultValue = "10") int numOfRows
  ) {
      System.out.println("keyword : " + keyword);
      PageInfo pageInfo = new PageInfo(adminReservationService.selectTotalCount(), currentPage, numOfRows);
      List<AdminReservationListDTO> reservations = adminReservationService.selectReservationList(new AdminReservationSearchDTO(pageInfo, keyword));
      
      reservations.forEach(value -> {
        System.out.println("value : " + value);
      });

      Map<String, Object> response = new HashMap<>();
      response.put("dataList", reservations);
      response.put("totalCount", pageInfo.getTotalCount());

      return ResponseEntity.ok(response);
  }

  @GetMapping("/{resCd}")
  public ResponseEntity<Object> reservationDetail(@PathVariable String resCd) {
    AdminReservationDetailDTO resVO = adminReservationService.findReservationByResCd(resCd);
    
    System.out.println(resVO);
    
    if(resVO != null) return ResponseEntity.status(HttpStatus.OK).body(resVO);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 상세 조회에 실패했습니다.");
  }
  
  @GetMapping("/cancel/list")
  public ResponseEntity<Object> reservationCancelList(
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "1") int currentPage,
      @RequestParam(defaultValue = "10") int numOfRows
  ) {
    System.out.println("keyword : " + keyword);
    PageInfo pageInfo = new PageInfo(adminReservationService.selectCancelTotalCount(), currentPage, numOfRows);
    List<AdminReservationCancelListDTO> reservations = adminReservationService.selectReservationCancelList(new AdminReservationSearchDTO(pageInfo, keyword));
    
    reservations.forEach(value -> {
      System.out.println("value : " + value);
    });

    Map<String, Object> response = new HashMap<>();
    response.put("dataList", reservations);
    response.put("totalCount", pageInfo.getTotalCount());

    return ResponseEntity.ok(response);
  }
  
  /**
   * 취소 예약 단건 조회
   * @param resCd
   * @return
   */
  @GetMapping("/cancel/detail/{resCd}")
  public ResponseEntity<Object> findReservationByResCd(@PathVariable String resCd) {
   System.out.println("취소 모달창 resCd : " + resCd);
    
   ReservationCancelDetailDTO reservationCancelDetailDTO = adminReservationService.findReservationCancelByResCd(resCd);
    
    System.out.println("취소 모달창 reservationCancelDetailDTO : " + reservationCancelDetailDTO);
    
    if(reservationCancelDetailDTO != null) return ResponseEntity.status(HttpStatus.OK).body(reservationCancelDetailDTO);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 상세 조회에 실패했습니다.");
  }
}
