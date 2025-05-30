package com.kh.clock.accommodation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.accommodation.service.AccomService;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
  
  private AccomService accomService;
  public AccommodationController(AccomService accomService) {
    this.accomService=accomService;
  }
  
  
}
