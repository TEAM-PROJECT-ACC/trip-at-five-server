package com.kh.clock.diary.repository;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiaryVO {
    int logSq;          //  LOG_SQ    NUMBER  일지번호, 시퀀스 사용    PRIMARY KEY LOG_LNO_PK
    String logTitle;    //  LOG_TITLE   VARCHAR2(50)    일지제목    NOT NULL    
    String logCont;     //  LOG_CONT    VARCHAR2(255)   일지내용    NOT NULL    
    Date logRegDt;      //  LOG_REG_DT  DATE    DEFAULT SYSDATE, 등록일    NOT NULL    
    int memNo;          //  MEM_NO  NUMBER  회원번호    FOREIGN KEY (MEM_NO) REFERENCES MEMBER_TB (MEM_SQ), NOT NULL    LOG_MNO_FK
}
