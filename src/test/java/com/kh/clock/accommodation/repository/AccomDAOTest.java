package com.kh.clock.accommodation.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.kh.clock.accommodation.repository.dto.AccomFilterDTO;
@SpringBootTest
class AccomDAOTest {
  @Autowired
  private SqlSession sqlSession;
  
  @Test
  void testSelectAccomList() {
    //fail("Not yet implemented");
    
    AccomFilterDTO accom = new AccomFilterDTO();
    accom.setKeyword("서울");
  }

}
