package com.kh.clock.accommodation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.accommodation.repository.AccomDTO;
import com.kh.clock.accommodation.repository.AccomListInfoDTO;
import com.kh.clock.accommodation.service.AccomService;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
  
  private AccomService accomService;
  
  public AccommodationController(AccomService accomService) {
    this.accomService=accomService;
  }
  
  @GetMapping
  public List<AccomDTO> selectAll(){
    List<AccomDTO> list = accomService.selectAll();
    return list;
  }
  
  @PostMapping
  public int createAccom(@RequestBody AccomDTO accom) {
    return accomService.createAccom(accom);
  }
  
  @PutMapping
  public int updateAccom(@RequestBody AccomDTO accom) {
    return accomService.updateAccom(accom);
  }
  
  @DeleteMapping
  public int deleteAccom(@RequestParam int accomSq) {
    return accomService.deleteAccom(accomSq);
  }
  
  @PostMapping("/search")
  public List<AccomDTO> searchAccom(@RequestBody AccomListInfoDTO searchFilter){
    return accomService.searchAccom(searchFilter);
  }
}
