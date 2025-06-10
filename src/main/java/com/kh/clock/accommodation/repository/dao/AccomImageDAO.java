package com.kh.clock.accommodation.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.accommodation.repository.dto.AccomAdminImageDTO;
import com.kh.clock.common.file.dto.ImageFileDTO;

@Repository
public class AccomImageDAO {
  
  private final SqlSession sqlSession;
  
  public AccomImageDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  public int insertAccomImage(AccomAdminImageDTO accomAdminImageDTO) {
    System.out.println("accomAdminImageDTO : " + accomAdminImageDTO);
    return sqlSession.insert("accomImageMapper.insertAccomImage", accomAdminImageDTO);
  }

  public List<AccomAdminImageDTO> findAccomImageByAccomSq(int accomNo) {
    return sqlSession.selectList("accomImageMapper.findAccomImageByAccomSq", accomNo);
  }

  public int updateAccomImage(AccomAdminImageDTO accomAdminImageDTO) {
    return sqlSession.update("accomImageMapper.updateAccomImage", accomAdminImageDTO);
  }

  public int deleteAccomImageByAccomSq(ImageFileDTO imageFileDTO) {
    return sqlSession.delete("accomImageMapper.deleteAccomImageByAccomSq", imageFileDTO);
  }
}
