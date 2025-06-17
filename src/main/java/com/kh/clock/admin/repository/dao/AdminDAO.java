package com.kh.clock.admin.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.member.domain.AdminVO;

@Repository
public class AdminDAO {
  private SqlSession sqlSession;
   
  public AdminDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  public AdminVO selectAdmin(int adminSq) {
    return sqlSession.selectOne("adminMapper.selectAdmin", adminSq);
  }

}
