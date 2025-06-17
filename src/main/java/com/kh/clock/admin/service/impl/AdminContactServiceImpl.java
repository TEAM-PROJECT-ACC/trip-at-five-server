package com.kh.clock.admin.service.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.kh.clock.admin.domain.AdminContactVO;
import com.kh.clock.admin.repository.dao.AdminContactDAO;
import com.kh.clock.admin.repository.dto.AdminContactListDTO;
import com.kh.clock.admin.service.AdminContactService;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.member.domain.AdminVO;

@Service
public class AdminContactServiceImpl implements AdminContactService {
  private AdminContactDAO adminContactDAO;
  
  public AdminContactServiceImpl(AdminContactDAO adminContactDAO) {
    this.adminContactDAO = adminContactDAO;
  }
  
  @Override
  public ArrayList<AdminContactVO> selectAdminContactList(AdminContactListDTO adminContactListDTO,
      PageInfo pageInfo) {
    ArrayList<AdminContactVO> contactList = (ArrayList<AdminContactVO>)adminContactDAO.selectAdminContactList(adminContactListDTO, pageInfo);
    return contactList;
  }

  @Override
  public int selectContactListCount(AdminVO admin) {    
    int totalCount = adminContactDAO.selectContactListCount(admin);
    return totalCount;
  }
  
}
