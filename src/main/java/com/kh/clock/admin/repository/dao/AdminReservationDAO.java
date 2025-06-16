package com.kh.clock.admin.repository.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.admin.repository.dto.AdminReservationCancelListDTO;
import com.kh.clock.admin.repository.dto.AdminReservationDetailDTO;
import com.kh.clock.admin.repository.dto.AdminReservationListDTO;
import com.kh.clock.reservation.domain.ReservationVO;

@Repository
public class AdminReservationDAO {
  
  private final SqlSession sqlSession;
  
  public AdminReservationDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  //전체 예약 수 조회
  public int selectReservationCount() {
      return sqlSession.selectOne("adminReservationMapper.selectReservationCount");
  }

  // RowBounds를 활용한 페이징 조회
  public List<AdminReservationListDTO> selectReservationList(String keyword, RowBounds rowBounds) {
      return sqlSession.selectList("adminReservationMapper.selectReservationList", keyword, rowBounds);
  }

  public ReservationVO findReservationByResCd(String resCode) {
    return sqlSession.selectOne("adminReservationMapper.findReservationByResCd", resCode);
  }

  public int selectCancelTotalCount() {
    return sqlSession.selectOne("adminReservationMapper.selectCancelTotalCount");
  }

  public List<AdminReservationCancelListDTO> selectReservationCancelList(String keyword, RowBounds rowBounds) {
    return sqlSession.selectList("adminReservationMapper.selectReservationCancelList", keyword, rowBounds);
  }

}
