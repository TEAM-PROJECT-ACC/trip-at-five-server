package com.kh.clock.reservation.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.kh.clock.reservation.domain.ReservationVO;
import com.kh.clock.reservation.repository.dao.ReservationDAO;
import com.kh.clock.reservation.repository.dto.ReservationCodeDTO;
import com.kh.clock.reservation.repository.dto.ReservationDTO;

@Service
public class ReservationServiceImpl implements ReservationService {

  @Value("${algorithm}")
  private String algorithm;
  
  @Value("${reservation.random.algorithm}")
  private String ranAlgorithm;
  
  private final ReservationDAO resDAO;
  
  public ReservationServiceImpl(ReservationDAO resDAO) {
    this.resDAO = resDAO;
  }
  
  @Override
  public String createReservationCode(ReservationCodeDTO resCodeDTO) {
    System.out.println(resCodeDTO);
//    System.out.println(ranAlgorithm);
    String salt = createSalt();

    String resCodeDeCoding = new StringBuilder()
        .append(resCodeDTO.getResEmail())
        .append(resCodeDTO.getResName())
        .append(resCodeDTO.getResPhone())
        .toString();

    System.out.println("salt : " + salt);
    
    String resCode = createResCode(resCodeDeCoding, salt);
    
    return resCode;
  }

  private String createSalt() {
    String salt = null;
    
    try {
      SecureRandom sr = SecureRandom.getInstance(ranAlgorithm);
      byte[] data = new byte[16];
      sr.nextBytes(data);
      
      salt = new String(Base64.getEncoder().encode(data));
      System.out.println("saltsalt : " + salt);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    
    return salt;
    
  }
  
  private String createResCode(String pwd, String salt) {
    System.out.println(pwd);
    System.out.println(salt);
    StringBuilder sb = new StringBuilder();
    String resCode = null;
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(pwd.getBytes());
      md.update(salt.getBytes());
      
      byte[] data = md.digest();
      
      for(byte b : data) {
        sb.append(String.format("%02x", b));
      }
      
      resCode = sb.toString();
      
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    
    return resCode;
  }

  @Override
  public int insertReservation(ReservationDTO reservationDTO, List<Integer> roomInfo) {
    int result = 0;
    
    for(int i = 0; i < roomInfo.size(); i++) {
      result += resDAO.insertReservation(
          new ReservationVO(
              reservationDTO.getResCode(),
              reservationDTO.getResEmail(),
              reservationDTO.getResName(),
              reservationDTO.getResPhone(),
              reservationDTO.getResNumOfPeo(),
              reservationDTO.getCheckInDt(),
              reservationDTO.getCheckOutDt(),
              roomInfo.get(i),
              reservationDTO.getMemNo()
         )
      );
    }
    
    return result;
  }

}
