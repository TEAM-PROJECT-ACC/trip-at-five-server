package com.kh.clock.accommodation.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;
@SpringBootTest
class AccomDAOTest {
  @Autowired
  private SqlSession sqlSession;
  
  @Test
  void testSelectAccomList() {
    //fail("Not yet implemented");
    
    AccomListInfoDTO accom = new AccomListInfoDTO();
    accom.setKeyword("서울");
  }

}
