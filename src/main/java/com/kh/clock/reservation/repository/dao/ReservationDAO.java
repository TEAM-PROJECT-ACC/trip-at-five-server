package com.kh.clock.reservation.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.reservation.domain.ReservationVO;

@Repository
public class ReservationDAO {

  private final SqlSession sqlSession;
  
  public ReservationDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insertReservation(ReservationVO reservationVO) {
    return sqlSession.insert("reservationMapper.insertReservation", reservationVO);
  }


}
