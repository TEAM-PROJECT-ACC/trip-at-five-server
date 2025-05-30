package com.kh.clock.common.pageInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * DB 조회 시 Pagination 처리를 위한 데이터 
*/
@Data
@AllArgsConstructor
public class PageInfo {
  private int totalCount;   // 전체 아이템 수
  private int pageNo;       // 현재 페이지 번호
  private int numOfRows;    // 페이지 당 표시 아이템 수
}
