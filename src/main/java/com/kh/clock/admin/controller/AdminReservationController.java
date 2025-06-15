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
import com.kh.clock.admin.repository.dto.AdminReservationDetailDTO;
import com.kh.clock.admin.repository.dto.AdminReservationListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationSearchDTO;
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

  @GetMapping("/{resCode}")
  public ResponseEntity<Object> reservationDetail(@PathVariable String resCode) {
    AdminReservationDetailDTO adminReservationDetailDTO = adminReservationService.findReservationByResCd(resCode);
    
    System.out.println(adminReservationDetailDTO);
    
    if(adminReservationDetailDTO != null) return ResponseEntity.status(HttpStatus.OK).body(adminReservationDetailDTO);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 상세 조회에 실패했습니다.");
  }
}
