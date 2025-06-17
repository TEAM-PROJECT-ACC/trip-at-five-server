package com.kh.clock.admin.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.admin.domain.AdminContactVO;
import com.kh.clock.admin.repository.dto.AdminContactDTO;
import com.kh.clock.admin.repository.dto.AdminContactListDTO;
import com.kh.clock.admin.service.AdminContactService;
import com.kh.clock.admin.service.AdminService;
import com.kh.clock.common.gson.CommonGson;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.member.domain.AdminVO;

@RestController
@RequestMapping("/admin/contact")
public class AdminContactController {
  private AdminContactService adminContactService;
  private AdminService adminService;
  
  public AdminContactController(AdminContactService adminContactService, AdminService adminService) {
    this.adminContactService = adminContactService;
    this.adminService = adminService;
  }
  
  @GetMapping("{adminSq}")
  public String selectAdminContactList(@PathVariable int adminSq, @RequestParam String searchParams) {
    AdminVO admin = adminService.selectAdmin(adminSq);
    PageInfo pageInfo = new PageInfo();
    
    JsonObject searchParamsObj = JsonParser.parseString(searchParams).getAsJsonObject();
    int pageNo = CommonGson.getJsonInt(searchParamsObj, "currentPage");
    int numOfRows = CommonGson.getJsonInt(searchParamsObj, "numOfRows");
    String keyword = CommonGson.getJsonString(searchParamsObj, "keyword");
    
    pageInfo.setKeyword(keyword);
    pageInfo.setPageNo(pageNo);
    pageInfo.setNumOfRows(numOfRows);
    
    System.out.println("admin contact list searchParams : " + searchParamsObj);
    AdminContactListDTO adminContactListDTO = new AdminContactListDTO();
    adminContactListDTO.setAdminSq(admin.getAdminSq());
    adminContactListDTO.setKeyword(keyword);
    ArrayList<AdminContactVO> contactList = adminContactService.selectAdminContactList(adminContactListDTO, pageInfo);
    int totalCount = 0;
    if(contactList.size() > 0) {
      totalCount = adminContactService.selectContactListCount(admin);
    }
    pageInfo.setTotalCount(totalCount);
    System.out.println("admin contact list totalCount : " + totalCount);
    // contactList, pageInfo, totalCount
    Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
    AdminContactDTO adminContactDTO = new AdminContactDTO();
    adminContactDTO.setContactList(contactList);
    adminContactDTO.setPageInfo(pageInfo);
    adminContactDTO.setTotalCount(totalCount);
    
    String responseData = gson.toJson(adminContactDTO);
    
    System.out.println("admin contact list : " + adminContactDTO);
    return responseData;
  }
}
