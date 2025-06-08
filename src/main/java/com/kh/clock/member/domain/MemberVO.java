package com.kh.clock.member.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

	private int memSq;
    private String memEmailId;
    private String memPwd;
    private String memNick;
    private String ckMemSt;
    private String memPhone;
    private String memAddr;
    private Date memRegDt;
    private String memSocUid;
    private String ckSocPlt;
    private String socRefTkn;
    private Date memUpdatedDt;
    private Date memInactiveDt;
    
}
