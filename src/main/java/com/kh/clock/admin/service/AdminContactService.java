package com.kh.clock.admin.service;

import java.util.ArrayList;
import com.kh.clock.admin.domain.AdminContactVO;
import com.kh.clock.admin.repository.dto.AdminContactListDTO;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.member.domain.AdminVO;

public interface AdminContactService {

  /**
   * @param adminSq
   * @return ArrayList<AdminContactDTO>
   */
  public ArrayList<AdminContactVO> selectAdminContactList(AdminContactListDTO adminContactListDTO,
      PageInfo pageInfo);

  /**
   * @param admin
   * @return int
   */
  public int selectContactListCount(AdminVO admin);
}
