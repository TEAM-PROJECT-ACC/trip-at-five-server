package com.kh.clock.accommodation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccommodationController {

  @GetMapping("/")
  public String test() {
    return "success";    
  }
}
