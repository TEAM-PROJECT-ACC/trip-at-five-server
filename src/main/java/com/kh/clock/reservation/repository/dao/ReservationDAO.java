package com.kh.clock.reservation.repository.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.order.repository.dto.OrderResInfoDTO;
import com.kh.clock.payment.repository.dto.CancelDTO;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import com.kh.clock.reservation.domain.ReservationVO;
import com.kh.clock.reservation.repository.dto.ResUserInfoDTO;

@Repository
public class ReservationDAO {

  private final SqlSession sqlSession;
  
  public ReservationDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insertReservation(ReservationVO reservationVO) {
    System.out.println("reservationVO : " + reservationVO);
    return sqlSession.insert("reservationMapper.insertReservation", reservationVO);
  }

  public List<OrderResInfoDTO> findReservationByResCd(List<String> resCdList) {
    List<OrderResInfoDTO> list = new ArrayList<>();
    
    resCdList.forEach(resCd -> {
      list.add(sqlSession.selectOne("reservationMapper.findReservationByResCd", resCd));
    });
    
    return list;
  }

  public ResUserInfoDTO findResUserInfo(String resCd) {
    return sqlSession.selectOne("reservationMapper.findResUserInfo", resCd);
  }


  public int updatePayState(List<String> resCdList) {
    int result = 0;
    
    for(int i = 0; i < resCdList.size(); i++) {
      result += sqlSession.update("reservationMapper.updatePayState", resCdList.get(i));
    }
    
    return result;
  }

  public int payStateCancel(CancelDTO cancelDTO) {
    return sqlSession.update("reservationMapper.payStateCancel", cancelDTO);
  }


}
