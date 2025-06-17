package com.kh.clock.admin.service.impl;

import org.springframework.stereotype.Service;
import com.kh.clock.admin.repository.dao.AdminDAO;
import com.kh.clock.admin.service.AdminService;
import com.kh.clock.member.domain.AdminVO;

@Service
public class AdminServiceImpl implements AdminService {
  private AdminDAO adminDAO;
  
  public AdminServiceImpl(AdminDAO adminDAO) {
    this.adminDAO = adminDAO;
  }

  @Override
  public AdminVO selectAdmin(int adminSq) {
    AdminVO admin = adminDAO.selectAdmin(adminSq);
    return admin;
  }
}
