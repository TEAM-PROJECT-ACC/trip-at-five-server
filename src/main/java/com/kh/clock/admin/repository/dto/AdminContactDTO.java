package com.kh.clock.admin.repository.dto;

import java.util.ArrayList;
import com.kh.clock.admin.domain.AdminContactVO;
import com.kh.clock.common.pageInfo.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminContactDTO {
  private ArrayList<AdminContactVO> contactList;
  private int totalCount;
  private PageInfo pageInfo;
}
