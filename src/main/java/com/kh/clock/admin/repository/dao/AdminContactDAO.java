package com.kh.clock.admin.repository.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.admin.domain.AdminContactVO;
import com.kh.clock.admin.repository.dto.AdminContactListDTO;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.member.domain.AdminVO;

@Repository
public class AdminContactDAO {
  private SqlSession sqlSession;
  
  public AdminContactDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public List<AdminContactVO> selectAdminContactList(AdminContactListDTO adminContactListDTO,
      PageInfo pageInfo) {
    RowBounds rowBounds = pageInfo.getRowBounds();
    return sqlSession.selectList("adminContactMapper.selectAdminContactList", adminContactListDTO, rowBounds);
  }

  public int selectContactListCount(AdminVO admin) {
    return sqlSession.selectOne("adminContactMapper.selectContactListCount", admin);
  } 
}
