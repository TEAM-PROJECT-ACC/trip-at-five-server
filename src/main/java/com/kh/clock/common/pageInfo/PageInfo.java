package com.kh.clock.common.pageInfo;

import org.apache.ibatis.session.RowBounds;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DB 조회 시 Pagination 처리를 위한 데이터 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
  private int totalCount;   // 전체 아이템 수
  private int pageNo;       // 현재 페이지 번호
  private int numOfRows;    // 페이지 당 표시 아이템 수
  private String keyword;
  
  public PageInfo(int totalCount, int pageNo, int numOfRows) {
     this.totalCount = totalCount;
     this.pageNo = pageNo;
     this.numOfRows = numOfRows;
  }
  
  // TODO: DB 조회 시 rowbounds 계산하는 곳에서 해당 메서드로 호출로 변경해야 함
  public RowBounds getRowBounds() {
    int offset = (pageNo - 1) * numOfRows; 
    RowBounds rowBounds = new RowBounds(offset, numOfRows);
    
    return rowBounds;
  }
}
