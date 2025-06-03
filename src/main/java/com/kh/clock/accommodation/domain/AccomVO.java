package com.kh.clock.accommodation.domain;
import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccomVO {
    private int accomSq;          // 숙박업소번호
    private String accomName;     // 숙박업소명
    private String accomDesc;     // 숙박업소 설명
    private double accomLon;      // 경도
    private double accomLat;      // 위도
    private String accomZipCode;  // 우편번호
    private String accomAddr;     // 주소
    private String accomPhone;    // 전화번호
    private Date accomRegDt;      // 등록일
    private String pubFacInfo;    // 공용편의시설 정보
    private String inRoomFacInfo; // 객실 내 편의시설 정보
    private String etcFacInfo;    // 기타시설 정보
    private int accomTypeNo;      // 숙박업소 유형번호
    private int locId;            // 지역번호
}
