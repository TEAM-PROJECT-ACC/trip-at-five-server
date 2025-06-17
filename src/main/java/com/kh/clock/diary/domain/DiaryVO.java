package com.kh.clock.diary.domain;

import java.sql.Date;
import lombok.Data;

@Data
public class DiaryVO {
    private int diarySq;            //  일지번호
    private String diaryTitle;      //  일지제목    
    private String diaryCont;       //  일지내용
    private Date diaryRegDt;        //  등록일
    private int memNo;              //  회원번호
}
