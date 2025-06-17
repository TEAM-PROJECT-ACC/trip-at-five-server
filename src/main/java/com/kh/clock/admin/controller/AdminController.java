package com.kh.clock.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.admin.service.AdminService;
import com.kh.clock.member.domain.AdminVO;

@RestController
@RequestMapping("/admin")
public class AdminController {
  private AdminService adminService;
  
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }
  
  @GetMapping("{adminSq}")
  public AdminVO selectAdmin(@PathVariable int adminSq) {
    AdminVO admin = adminService.selectAdmin(adminSq);
    return admin;
  }
}
