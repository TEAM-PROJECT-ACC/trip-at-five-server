-- 25/05/13 1차 완성
-- 25/05/17 2차 완성
-- 25/05/19 INSERT문 추가


-----------------------------------------------------------------------------



-- 변경사항이 있을 시 ISSUE에 필요한 부분 등록해주시면 추가하겠습니다. - DBA 임성준



-----------------------------------------------------------------------------

-- MEMBER_TB 회원 테이블
DROP TABLE MEMBER_TB;
CREATE TABLE MEMBER_TB (
    MEM_SQ NUMBER,
    MEM_EMAIL_ID VARCHAR2(100) NOT NULL,
    MEM_PWD VARCHAR2(30) NOT NULL,
    MEM_NICK VARCHAR2(50) NOT NULL,
    CK_MEM_ST VARCHAR2(10) DEFAULT 'ACTIVE' NOT NULL,
    MEM_PHONE VARCHAR2(11),
    MEM_ADDR VARCHAR2(50),
    MEM_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    MEM_SOC_UID VARCHAR2(50),
    CK_SOC_PLT VARCHAR2(10) DEFAULT 'NORMAL' NOT NULL,
    SOC_REF_TKN VARCHAR2(100),
    MEM_UPDATED_DT DATE,
    MEM_INACTIVE_DT DATE,
    
    CONSTRAINT MEM_MNO_PK PRIMARY KEY (MEM_SQ),
    CONSTRAINT MEM_EMAIL_UQ UNIQUE (MEM_EMAIL_ID),
    CONSTRAINT MEM_NICK_UQ UNIQUE (MEM_NICK),
    CONSTRAINT MEM_STATE_CK CHECK(CK_MEM_ST IN ('ACTIVE', 'INACTIVE')),
    CONSTRAINT MEM_PHONE_UQ UNIQUE (MEM_PHONE),
    CONSTRAINT MEM_UID_UQ UNIQUE (MEM_SOC_UID),
    CONSTRAINT MEM_PLT_CK CHECK(CK_SOC_PLT IN ('NORMAL', 'KAKAO', 'GOOGLE', 'NAVER')),
    CONSTRAINT MEM_RTK_UQ UNIQUE (SOC_REF_TKN)
);

COMMENT ON COLUMN MEMBER_TB.MEM_SQ IS '회원번호';
COMMENT ON COLUMN MEMBER_TB.MEM_EMAIL_ID IS '회원이메일';
COMMENT ON COLUMN MEMBER_TB.MEM_PWD IS '회원비밀번호';
COMMENT ON COLUMN MEMBER_TB.MEM_NICK IS '회원닉네임';
COMMENT ON COLUMN MEMBER_TB.CK_MEM_ST IS '회원상태';
COMMENT ON COLUMN MEMBER_TB.MEM_PHONE IS '회원전화번호';
COMMENT ON COLUMN MEMBER_TB.MEM_ADDR IS '회원주소';
COMMENT ON COLUMN MEMBER_TB.MEM_REG_DT IS '가입날짜';
COMMENT ON COLUMN MEMBER_TB.MEM_SOC_UID IS '소셜회원 사용자 식별 값';
COMMENT ON COLUMN MEMBER_TB.CK_SOC_PLT IS '소셜로그인 플랫폼';
COMMENT ON COLUMN MEMBER_TB.SOC_REF_TKN IS '리프레쉬 토큰값';
COMMENT ON COLUMN MEMBER_TB.MEM_UPDATED_DT IS '정보수정날짜';
COMMENT ON COLUMN MEMBER_TB.MEM_INACTIVE_DT IS '비활성화날짜';

DROP SEQUENCE MEM_SEQ;
CREATE SEQUENCE MEM_SEQ;
-----------------------------------------------------------------------------
-- DAYLY_LOG_TB 일지 테이블
DROP TABLE DAYLY_LOG_TB;
CREATE TABLE DAYLY_LOG_TB (
    LOG_SQ NUMBER,
    LOG_TIT VARCHAR2(50) NOT NULL,
    LOG_CONT VARCHAR2(255) NOT NULL,
    LOG_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    MEM_NO NUMBER NOT NULL,
    
    CONSTRAINT LOG_LNO_PK PRIMARY KEY (LOG_SQ),
    CONSTRAINT DAILY_MEM_NO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN DAYLY_LOG_TB.LOG_SQ IS '일지번호';
COMMENT ON COLUMN DAYLY_LOG_TB.LOG_TIT IS '일지제목';
COMMENT ON COLUMN DAYLY_LOG_TB.LOG_CONT IS '일지내용';
COMMENT ON COLUMN DAYLY_LOG_TB.LOG_REG_DT IS '등록일';
COMMENT ON COLUMN DAYLY_LOG_TB.MEM_NO IS '회원번호';

DROP SEQUENCE LOG_SEQ;
CREATE SEQUENCE LOG_SEQ;
-----------------------------------------------------------------------------
-- INQUIRY_CATEGORY_TB 문의유형카테고리 테이블

DROP TABLE INQUIRY_CATEGORY_TB;
CREATE TABLE INQUIRY_CATEGORY_TB (
    INQ_CTG_CD VARCHAR2(20),
    INQ_CTG_NAME VARCHAR2(255) NOT NULL,
    
    CONSTRAINT INQ_ICD_PK PRIMARY KEY (INQ_CTG_CD)
);

COMMENT ON COLUMN INQUIRY_CATEGORY_TB.INQ_CTG_CD IS '문의유형코드';
COMMENT ON COLUMN INQUIRY_CATEGORY_TB.INQ_CTG_NAME IS '문의유형명';

-----------------------------------------------------------------------------
-- ADMIN_TB 관리자 테이블
DROP TABLE ADMIN_TB;
CREATE TABLE ADMIN_TB (
    ADMIN_SQ NUMBER,
    ADMIN_EMAIL_ID VARCHAR2(100) NOT NULL,
    ADMIN_PWD VARCHAR2(30) NOT NULL,
    ADMIN_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    INQ_CTG_CD VARCHAR2(20),
    
    CONSTRAINT ADMIN_ADNO_PK PRIMARY KEY (ADMIN_SQ),
    CONSTRAINT ADMIN_EMAIL_UQ UNIQUE (ADMIN_EMAIL_ID),
    CONSTRAINT ADMIN_INQTP_FK FOREIGN KEY (INQ_CTG_CD)
        REFERENCES INQUIRY_CATEGORY_TB (INQ_CTG_CD) ON DELETE SET NULL
);

COMMENT ON COLUMN ADMIN_TB.ADMIN_SQ IS '관리자번호';
COMMENT ON COLUMN ADMIN_TB.ADMIN_EMAIL_ID IS '관리자이메일';
COMMENT ON COLUMN ADMIN_TB.ADMIN_PWD IS '관리자비밀번호';
COMMENT ON COLUMN ADMIN_TB.ADMIN_REG_DT IS '등록일';
COMMENT ON COLUMN ADMIN_TB.INQ_CTG_CD IS '담당문의유형';

DROP SEQUENCE ADMIN_SEQ;
CREATE SEQUENCE ADMIN_SEQ;

-----------------------------------------------------------------------------

-- CHAT_ROOM_TB 채팅방 테이블
DROP TABLE CHAT_ROOM_TB;
CREATE TABLE CHAT_ROOM_TB (
    CHAT_ROOM_SQ NUMBER,
    CK_CHAT_ST VARCHAR2(10) DEFAULT 'ACTIVE' NOT NULL,
    CHAT_ROOM_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    MEM_NO NUMBER,
    ADMIN_NO NUMBER NOT NULL,
    INQ_CTG_CD VARCHAR2(20) NULL, 
    
    CONSTRAINT CROOM_CRNO_PK PRIMARY KEY (CHAT_ROOM_SQ),
    CONSTRAINT CROOM_STATE_CK CHECK(CK_CHAT_ST IN ('ACTIVE', 'INACTIVE')),
    CONSTRAINT CROOM_MNO_FK FOREIGN KEY (MEM_NO) REFERENCES MEMBER_TB (MEM_SQ),    
    CONSTRAINT CROOM_ADNO_FK FOREIGN KEY (ADMIN_NO) REFERENCES ADMIN_TB (ADMIN_SQ),
    CONSTRAINT CROOM_INQTP_FK FOREIGN KEY (INQ_CTG_CD)
        REFERENCES INQUIRY_CATEGORY_TB (INQ_CTG_CD) ON DELETE SET NULL
);

COMMENT ON COLUMN CHAT_ROOM_TB.CHAT_ROOM_SQ IS '채팅방번호';
COMMENT ON COLUMN CHAT_ROOM_TB.CK_CHAT_ST IS '채팅방상태';
COMMENT ON COLUMN CHAT_ROOM_TB.CHAT_ROOM_REG_DT IS '등록일';
COMMENT ON COLUMN CHAT_ROOM_TB.MEM_NO IS '회원번호';
COMMENT ON COLUMN CHAT_ROOM_TB.ADMIN_NO IS '관리자번호';
COMMENT ON COLUMN CHAT_ROOM_TB.INQ_CTG_CD IS '담당문의유형코드';

DROP SEQUENCE CHAT_ROOM_SEQ;
CREATE SEQUENCE CHAT_ROOM_SEQ;

-----------------------------------------------------------------------------

-- CHAT_MESSAGE_TB 채팅메시지 테이블
DROP TABLE CHAT_MESSAGE_TB;
CREATE TABLE CHAT_MESSAGE_TB (
    CHAT_MSG_SQ NUMBER,
    CHAT_MSG_CONT VARCHAR2(4000) NOT NULL,
    SEND_DT DATE DEFAULT SYSDATE NOT NULL,
    CK_SENDER_TYPE VARCHAR2(10) DEFAULT 'NON-M' NOT NULL,
    SENDER_EMAIL VARCHAR2(100) NOT NULL,
    CHAT_ROOM_NO NUMBER NOT NULL,
    
    CONSTRAINT CMSG_MSGNO_PK PRIMARY KEY (CHAT_MSG_SQ),
    CONSTRAINT CMSG_TYPE_CK CHECK(CK_SENDER_TYPE IN ('NON-M', 'MEMBER', 'ADMIN')),
    CONSTRAINT CMSG_RNO_FK FOREIGN KEY (CHAT_ROOM_NO) REFERENCES CHAT_ROOM_TB (CHAT_ROOM_SQ) 
);

COMMENT ON COLUMN CHAT_MESSAGE_TB.CHAT_MSG_SQ IS '메시지번호';
COMMENT ON COLUMN CHAT_MESSAGE_TB.CHAT_MSG_CONT IS '채팅메시지';
COMMENT ON COLUMN CHAT_MESSAGE_TB.SEND_DT IS '보낸날짜';
COMMENT ON COLUMN CHAT_MESSAGE_TB.CK_SENDER_TYPE IS '회원유형';
COMMENT ON COLUMN CHAT_MESSAGE_TB.SENDER_EMAIL IS '메시지 보낸 사용자 이메일';
COMMENT ON COLUMN CHAT_MESSAGE_TB.CHAT_ROOM_NO IS '채팅방번호';

DROP SEQUENCE CHAT_MSG_SEQ;
CREATE SEQUENCE CHAT_MSG_SEQ;

-----------------------------------------------------------------------------

-- COUPON_TB 회원 쿠폰 테이블
DROP TABLE COUPON_TB;
CREATE TABLE COUPON_TB (
    COUPON_SQ NUMBER,
    COUPON_NAME VARCHAR2(30) NOT NULL,
    COUPON_PRICE NUMBER DEFAULT 0 NOT NULL,
    COUPON_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    
    CONSTRAINT COUPON_NO_PK PRIMARY KEY (COUPON_SQ)
);

COMMENT ON COLUMN COUPON_TB.COUPON_SQ IS '쿠폰번호';
COMMENT ON COLUMN COUPON_TB.COUPON_NAME IS '쿠폰명';
COMMENT ON COLUMN COUPON_TB.COUPON_PRICE IS '쿠폰가격';
COMMENT ON COLUMN COUPON_TB.COUPON_REG_DT IS '등록일';

DROP SEQUENCE COUPON_SEQ;
CREATE SEQUENCE COUPON_SEQ;

-----------------------------------------------------------------------------

-- CHALLENGE_TB 챌린지 테이블
DROP TABLE CHALLENGE_TB;
CREATE TABLE CHALLENGE_TB (
    CHAL_SQ NUMBER,
    CHAL_NAME VARCHAR2(50) NOT NULL,
    CHAL_COND VARCHAR2(50) NOT NULL,
    CHAL_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    COUPON_NO NUMBER NOT NULL,
    ADMIN_NO NUMBER NOT NULL,
    
    CONSTRAINT CHAL_NO_PK PRIMARY KEY (CHAL_SQ),
    CONSTRAINT CHAL_NAME_UQ UNIQUE (CHAL_NAME),
    CONSTRAINT CHAL_COUPON_FK FOREIGN KEY (COUPON_NO)
        REFERENCES COUPON_TB (COUPON_SQ) ON DELETE SET NULL,
    CONSTRAINT CHAL_ADNO_FK FOREIGN KEY (ADMIN_NO)
        REFERENCES ADMIN_TB (ADMIN_SQ) ON DELETE SET NULL
);

COMMENT ON COLUMN CHALLENGE_TB.CHAL_SQ IS '챌린지번호';
COMMENT ON COLUMN CHALLENGE_TB.CHAL_NAME IS '챌린지명';
COMMENT ON COLUMN CHALLENGE_TB.CHAL_COND IS '챌린지조건값';
COMMENT ON COLUMN CHALLENGE_TB.CHAL_REG_DT IS '등록일';
COMMENT ON COLUMN CHALLENGE_TB.COUPON_NO IS '쿠폰번호';
COMMENT ON COLUMN CHALLENGE_TB.ADMIN_NO IS '관리자번호';

DROP SEQUENCE CHAL_SEQ;
CREATE SEQUENCE CHAL_SEQ;

-----------------------------------------------------------------------------

-- CHALLENGE_MEMBER_HISTORY_TB 챌린지 이용 내역 테이블
DROP TABLE CHALLENGE_MEMBER_HISTORY_TB;
CREATE TABLE CHALLENGE_MEMBER_HISTORY_TB (
    CHAL_HX_SQ NUMBER,
    CHAL_HX_VAL NUMBER NOT NULL,
    CHAL_HX_DT DATE DEFAULT SYSDATE NOT NULL,
    CHAL_NO NUMBER NOT NULL,
    MEM_NO NUMBER NOT NULL,
    
    CONSTRAINT CHX_NO_PK PRIMARY KEY (CHAL_HX_SQ),
    CONSTRAINT CHX_CNO_FK FOREIGN KEY (CHAL_NO)
        REFERENCES CHALLENGE_TB (CHAL_SQ) ON DELETE CASCADE,
    CONSTRAINT CHX_MNO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN CHALLENGE_MEMBER_HISTORY_TB.CHAL_HX_SQ IS '이용내역번호';
COMMENT ON COLUMN CHALLENGE_MEMBER_HISTORY_TB.CHAL_HX_VAL IS '회원 진행 상태값';
COMMENT ON COLUMN CHALLENGE_MEMBER_HISTORY_TB.CHAL_HX_DT IS '챌린지 시작일';
COMMENT ON COLUMN CHALLENGE_MEMBER_HISTORY_TB.CHAL_NO IS '챌린지번호';
COMMENT ON COLUMN CHALLENGE_MEMBER_HISTORY_TB.MEM_NO IS '회원번호';

DROP SEQUENCE CHAL_HISTORY_SEQ;
CREATE SEQUENCE CHAL_HISTORY_SEQ;

-----------------------------------------------------------------------------
-- CHALLENGE_COMPLETED_TB 챌린지 완료 내역 테이블
DROP TABLE CHALLENGE_COMPLETED_TB;
CREATE TABLE CHALLENGE_COMPLETED_TB (
    CHAL_COMPLETED_SQ NUMBER,
    CK_COUPON_ST VARCHAR2(10) NOT NULL,
    CHAL_COMPLETED_DT DATE DEFAULT SYSDATE NOT NULL,
    MEM_NO NUMBER NOT NULL,
    CHAL_NO NUMBER NOT NULL,
    
    CONSTRAINT CCOM_STATE_CK CHECK(CK_COUPON_ST IN ('UNUSED', 'USED')),
    CONSTRAINT CCOM_MNO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE CASCADE,
    CONSTRAINT CCOM_CNO_FK FOREIGN KEY (CHAL_NO)
        REFERENCES CHALLENGE_TB (CHAL_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN CHALLENGE_COMPLETED_TB.CHAL_COMPLETED_SQ IS '챌린지번호';
COMMENT ON COLUMN CHALLENGE_COMPLETED_TB.CK_COUPON_ST IS '쿠폰 상태';
COMMENT ON COLUMN CHALLENGE_COMPLETED_TB.CHAL_COMPLETED_DT IS '완료 날짜';
COMMENT ON COLUMN CHALLENGE_COMPLETED_TB.MEM_NO IS '회원번호';
COMMENT ON COLUMN CHALLENGE_COMPLETED_TB.CHAL_NO IS '챌린지번호';

DROP SEQUENCE CHAL_COMPLETED_SEQ;
CREATE SEQUENCE CHAL_COMPLETED_SEQ;

-----------------------------------------------------------------------------

-- ACCOMMODATION_TYPE_TB 숙박업소 유형 테이블
DROP TABLE ACCOMMODATION_TYPE_TB;
CREATE TABLE ACCOMMODATION_TYPE_TB (
    ACCOM_TYPE_SQ NUMBER,
    ACCOM_TYPE_NAME VARCHAR2(30) NOT NULL,
    
    CONSTRAINT ACCTP_TPNO_PK PRIMARY KEY (ACCOM_TYPE_SQ) 
);

COMMENT ON COLUMN ACCOMMODATION_TYPE_TB.ACCOM_TYPE_SQ IS '숙박업소유형번호';
COMMENT ON COLUMN ACCOMMODATION_TYPE_TB.ACCOM_TYPE_NAME IS '숙박업소유형명';

DROP SEQUENCE ACCOM_TYPE_SEQ;
CREATE SEQUENCE ACCOM_TYPE_SEQ;

-----------------------------------------------------------------------------

-- LOCATION_TB 지역 테이블
DROP TABLE LOCATION_TB;
CREATE TABLE LOCATION_TB (
    LOC_ID NUMBER,
    LOC_NAME VARCHAR2(30) NOT NULL,
    
    CONSTRAINT LOC_ID_PK PRIMARY KEY (LOC_ID)
);

COMMENT ON COLUMN LOCATION_TB.LOC_ID IS '지역번호';
COMMENT ON COLUMN LOCATION_TB.LOC_NAME IS '지역명';

-----------------------------------------------------------------------------

-- LOCATION_SEARCH_TB
DROP TABLE LOCATION_SEARCH_TB;
CREATE TABLE LOCATION_SEARCH_TB (
    LOC_ID NUMBER NOT NULL,
    LOC_SEO_CNT NUMBER DEFAULT 0,
    
    CONSTRAINT LOC_SEO_ID_FK FOREIGN KEY (LOC_ID)
        REFERENCES LOCATION_TB (LOC_ID) ON DELETE CASCADE,
    CONSTRAINT LOC_SEO_ID_PK PRIMARY KEY (LOC_ID)
);

COMMENT ON COLUMN LOCATION_SEARCH_TB.LOC_ID IS '지역번호';
COMMENT ON COLUMN LOCATION_SEARCH_TB.LOC_SEO_CNT IS '검색횟수';

-----------------------------------------------------------------------------

-- ACCOMMODATION_TB 숙박업소 테이블
DROP TABLE ACCOMMODATION_TB;
CREATE TABLE ACCOMMODATION_TB (
    ACCOM_SQ NUMBER,
    ACCOM_NAME VARCHAR2(100),
    ACCOM_DESC VARCHAR2(4000),
    ACCOM_LON NUMBER DEFAULT 0,
    ACCOM_LAT NUMBER DEFAULT 0,
    ACCOM_ZIP_CODE VARCHAR2(10),
    ACCOM_ADDR VARCHAR2(255),
    ACCOM_PHONE VARCHAR2(100),
    ACCOM_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    PUB_FAC_INFO VARCHAR2(200),
    IN_ROOM_FAC_INFO VARCHAR2(200),
    ETC_FAC_INFO VARCHAR2(200),
    ACCOM_TYPE_NO NUMBER,
    LOC_ID NUMBER,
    
    CONSTRAINT ACCOM_ANO_PK PRIMARY KEY (ACCOM_SQ),
    CONSTRAINT ACCOM_TPNO_FK FOREIGN KEY (ACCOM_TYPE_NO)
        REFERENCES ACCOMMODATION_TYPE_TB (ACCOM_TYPE_SQ) ON DELETE SET NULL,
    CONSTRAINT ACCOM_LOC_FK FOREIGN KEY (LOC_ID)
        REFERENCES LOCATION_TB (LOC_ID) ON DELETE SET NULL
);

COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_SQ IS '숙박업소번호';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_NAME IS '숙박업소명';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_DESC IS '숙박업소설명';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_LON IS '경도';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_LAT IS '위도';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_ZIP_CODE IS '우편번호';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_ADDR IS '주소';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_PHONE IS '전화번호';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_REG_DT IS '등록일';
COMMENT ON COLUMN ACCOMMODATION_TB.PUB_FAC_INFO IS '공용편의시설정보';
COMMENT ON COLUMN ACCOMMODATION_TB.IN_ROOM_FAC_INFO IS '객실 내 시설 정보';
COMMENT ON COLUMN ACCOMMODATION_TB.ETC_FAC_INFO IS '기타시설정보';
COMMENT ON COLUMN ACCOMMODATION_TB.ACCOM_TYPE_NO IS '숙박업소유형번호';
COMMENT ON COLUMN ACCOMMODATION_TB.LOC_ID IS '지역번호';

DROP SEQUENCE ACCOM_SEQ;
CREATE SEQUENCE ACCOM_SEQ;

---- 숙박 유형 NULL 값 초기 값 설정
--UPDATE ACCOMMODATION_TB SET
--    ACCOM_TYPE_NO = 1
--WHERE ACCOM_NAME LIKE '%모텔%';
--UPDATE ACCOMMODATION_TB SET
--    ACCOM_TYPE_NO = 2
--WHERE ACCOM_NAME LIKE '%호텔%';
--UPDATE ACCOMMODATION_TB SET
--    ACCOM_TYPE_NO = 3
--WHERE ACCOM_NAME LIKE '%펜션%';
--
---- NOT NULL 제약조건 추가
--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_NAME NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_DESC NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_ADDR NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_PHONE NOT NULL;
--
--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_TYPE_NO NOT NULL;
--
--ALTER TABLE ACCOMMODATION_TB
--MODIFY LOC_ID NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_ZIP_CODE NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_LON NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_LAT NOT NULL;

--ALTER TABLE ACCOMMODATION_TB
--MODIFY ACCOM_REG_DT NOT NULL;

-----------------------------------------------------------------------------

-- ACCOMMODATION_IMAGE_TB 숙박업소 이미지 테이블
DROP TABLE ACCOMMODATION_IMAGE_TB;
CREATE TABLE ACCOMMODATION_IMAGE_TB (
    ACCOM_IMG_SQ NUMBER,
    ACCOM_IMG_ORG_NAME VARCHAR2(500) NOT NULL,
    ACCOM_IMG_PATH_NAME VARCHAR2(1000) DEFAULT 'assets/resources/upload/acc/' NOT NULL,
    ACCOM_IMG_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    ACCOM_NO NUMBER NOT NULL,
    
    CONSTRAINT ACCIMG_INO_PK PRIMARY KEY (ACCOM_IMG_SQ),
    CONSTRAINT ACCIMG_ANO_FK FOREIGN KEY (ACCOM_NO)
        REFERENCES ACCOMMODATION_TB (ACCOM_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN ACCOMMODATION_IMAGE_TB.ACCOM_IMG_SQ IS '숙박업소이미지번호';
COMMENT ON COLUMN ACCOMMODATION_IMAGE_TB.ACCOM_IMG_ORG_NAME IS '이미지 원본 이름';
COMMENT ON COLUMN ACCOMMODATION_IMAGE_TB.ACCOM_IMG_PATH_NAME IS '이미지 경로';
COMMENT ON COLUMN ACCOMMODATION_IMAGE_TB.ACCOM_IMG_REG_DT IS '등록일';
COMMENT ON COLUMN ACCOMMODATION_IMAGE_TB.ACCOM_NO IS '숙박업소번호';

DROP SEQUENCE ACCOM_IMG_SEQ;
CREATE SEQUENCE ACCOM_IMG_SEQ;

-----------------------------------------------------------------------------

-- ROOM_TB 객실 테이블
DROP TABLE ROOM_TB;
CREATE TABLE ROOM_TB (
    ROOM_SQ NUMBER,
    ROOM_NAME VARCHAR2(50) NOT NULL,
    ROOM_PRICE NUMBER DEFAULT 0 NOT NULL,
    ROOM_CHK_IN VARCHAR2(10) NOT NULL,
    ROOM_CHK_OUT VARCHAR2(10) NOT NULL,
    ROOM_STD_PPL NUMBER DEFAULT 0 NOT NULL,
    ROOM_MAX_PPL NUMBER DEFAULT 0 NOT NULL,
    ROOM_CNT NUMBER DEFAULT 0 NOT NULL,
    ROOM_DESC VARCHAR2(255) NOT NULL,
    ROOM_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    ACCOM_NO NUMBER NOT NULL,
    
    CONSTRAINT ROOM_RNO_PK PRIMARY KEY (ROOM_SQ),
    CONSTRAINT ROOM_ANO_FK FOREIGN KEY (ACCOM_NO)
        REFERENCES ACCOMMODATION_TB (ACCOM_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN ROOM_TB.ROOM_SQ IS '객실번호';
COMMENT ON COLUMN ROOM_TB.ROOM_NAME IS '객실명';
COMMENT ON COLUMN ROOM_TB.ROOM_PRICE IS '객실가격';
COMMENT ON COLUMN ROOM_TB.ROOM_CHK_IN IS '체크인 시간';
COMMENT ON COLUMN ROOM_TB.ROOM_CHK_OUT IS '체크아웃 시간';
COMMENT ON COLUMN ROOM_TB.ROOM_STD_PPL IS '기준인원';
COMMENT ON COLUMN ROOM_TB.ROOM_MAX_PPL IS '최대인원';
COMMENT ON COLUMN ROOM_TB.ROOM_CNT IS '객실 수';
COMMENT ON COLUMN ROOM_TB.ROOM_DESC IS '객실설명';
COMMENT ON COLUMN ROOM_TB.ROOM_REG_DT IS '등록일';
COMMENT ON COLUMN ROOM_TB.ACCOM_NO IS '숙박업소번호';

DROP SEQUENCE ROOM_SEQ;
CREATE SEQUENCE ROOM_SEQ;

-----------------------------------------------------------------------------

-- ROOM_IMAGE_TB 객실 이미지 테이블
DROP TABLE ROOM_IMAGE_TB;
CREATE TABLE ROOM_IMAGE_TB (
    ROOM_IMG_SQ NUMBER,
    ROOM_IMG_ORG_NAME VARCHAR2(500) NOT NULL,
    ROOM_IMG_PATH_NAME VARCHAR2(1000) DEFAULT 'assets/resources/upload/acc/' NOT NULL,
    ROOM_IMG_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    ROOM_NO NUMBER NOT NULL,
    
    CONSTRAINT RIMG_INO_PK PRIMARY KEY (ROOM_IMG_SQ),
    CONSTRAINT RIMG_RNO_FK FOREIGN KEY (ROOM_NO)
        REFERENCES ROOM_TB (ROOM_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN ROOM_IMAGE_TB.ROOM_IMG_SQ IS '객실이미지번호';
COMMENT ON COLUMN ROOM_IMAGE_TB.ROOM_IMG_ORG_NAME IS '이미지 원본 이름';
COMMENT ON COLUMN ROOM_IMAGE_TB.ROOM_IMG_PATH_NAME IS '이미지 경로';
COMMENT ON COLUMN ROOM_IMAGE_TB.ROOM_IMG_REG_DT IS '등록일';
COMMENT ON COLUMN ROOM_IMAGE_TB.ROOM_NO IS '객실번호';

DROP SEQUENCE ROOM_IMG_SEQ;
CREATE SEQUENCE ROOM_IMG_SEQ;

-----------------------------------------------------------------------------

-- CART_TB 장바구니 테이블
DROP TABLE CART_TB;
CREATE TABLE CART_TB (
    CART_SQ NUMBER,
    CART_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    MEM_NO NUMBER NOT NULL,
    ROOM_NO NUMBER NOT NULL,
    
    CONSTRAINT CART_CSQ_PK PRIMARY KEY (CART_SQ),
    CONSTRAINT CART_MNO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE CASCADE,
    CONSTRAINT CART_RNO_FK FOREIGN KEY (ROOM_NO)
        REFERENCES ROOM_TB (ROOM_SQ) ON DELETE SET NULL
);

COMMENT ON COLUMN CART_TB.CART_SQ IS '장바구니번호';
COMMENT ON COLUMN CART_TB.CART_REG_DT IS '장바구니등록날짜';
COMMENT ON COLUMN CART_TB.MEM_NO IS '회원번호';
COMMENT ON COLUMN CART_TB.ROOM_NO IS '객실번호';

DROP SEQUENCE CART_SEQ;
CREATE SEQUENCE CART_SEQ;

-----------------------------------------------------------------------------

-- RESERVATION_TB 예약 테이블
DROP TABLE RESERVATION_TB;
CREATE TABLE RESERVATION_TB (
    RES_CD VARCHAR2(10),
    RES_EMAIL_ID VARCHAR2(100) NOT NULL,
    RES_NAME VARCHAR2(20) NOT NULL,
    RES_PHONE VARCHAR2(11) NOT NULL,
    CHECK_IN_DT DATE NOT NULL,
    CHECK_OUT_DT DATE NOT NULL,
    CK_RES_ST VARCHAR2(10) DEFAULT 'COMPLETED',
    RES_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    ROOM_NO NUMBER NOT NULL,
    MEM_NO NUMBER NOT NULL,
    
    CONSTRAINT RES_CODE_PK PRIMARY KEY (RES_CD),
    CONSTRAINT RES_STATE_CK CHECK(CK_RES_ST IN ('COMPLETED', 'PROCESSING', 'CANCEL')),
    CONSTRAINT RES_RNO_FK FOREIGN KEY (ROOM_NO)
        REFERENCES ROOM_TB (ROOM_SQ) ON DELETE CASCADE,
    CONSTRAINT RES_MNO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE SET NULL
);

COMMENT ON COLUMN RESERVATION_TB.RES_CD IS '예약코드';
COMMENT ON COLUMN RESERVATION_TB.RES_EMAIL_ID IS '사용자이메일';
COMMENT ON COLUMN RESERVATION_TB.RES_NAME IS '예약자명';
COMMENT ON COLUMN RESERVATION_TB.RES_PHONE IS '예약자 전화번호';
COMMENT ON COLUMN RESERVATION_TB.CHECK_IN_DT IS '체크인 날짜';
COMMENT ON COLUMN RESERVATION_TB.CHECK_OUT_DT IS '체크아웃 날짜';
COMMENT ON COLUMN RESERVATION_TB.CK_RES_ST IS '예약상태';
COMMENT ON COLUMN RESERVATION_TB.RES_REG_DT IS '예약 생성일';
COMMENT ON COLUMN RESERVATION_TB.ROOM_NO IS '객실번호';
COMMENT ON COLUMN RESERVATION_TB.MEM_NO IS '회원번호';

-----------------------------------------------------------------------------

-- 결제 테이블

-----------------------------------------------------------------------------

-- MEMBER_EXPERIENCE_TB 회원 경험치 내역 테이블
DROP TABLE MEMBER_EXPERIENCE_TB;
CREATE TABLE MEMBER_EXPERIENCE_TB (
    MEM_NO NUMBER,
    RES_CD VARCHAR2(10),
    MEM_EXP_VAL NUMBER DEFAULT 0 NOT NULL,
    MEM_EXP_DT DATE DEFAULT SYSDATE NOT NULL,
    
    CONSTRAINT MXP_MNO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE CASCADE,
    CONSTRAINT MXP_RCD_FK FOREIGN KEY (RES_CD)
        REFERENCES RESERVATION_TB (RES_CD) ON DELETE CASCADE,
    CONSTRAINT MXP_PK PRIMARY KEY (MEM_NO, RES_CD)
);

COMMENT ON COLUMN MEMBER_EXPERIENCE_TB.MEM_NO IS '회원번호';
COMMENT ON COLUMN MEMBER_EXPERIENCE_TB.RES_CD IS '예약코드';
COMMENT ON COLUMN MEMBER_EXPERIENCE_TB.MEM_EXP_VAL IS '획득 경험치';
COMMENT ON COLUMN MEMBER_EXPERIENCE_TB.MEM_EXP_DT IS '획득 날짜';

-----------------------------------------------------------------------------
-- MEMBER_LEVEL_TB 회원 레벨 테이블
DROP TABLE MEMBER_LEVEL_TB;
CREATE TABLE MEMBER_LEVEL_TB (
    MEM_NO NUMBER,
    MEM_LVL NUMBER DEFAULT 1 NOT NULL,
    CK_MEM_EXP NUMBER DEFAULT 0 NOT NULL,
    
    CONSTRAINT MLV_EXP_CK CHECK(CK_MEM_EXP <= 99),
    CONSTRAINT MLV_MNO_FK FOREIGN KEY (MEM_NO)
        REFERENCES MEMBER_TB (MEM_SQ) ON DELETE CASCADE,
    CONSTRAINT MLV_MNO_PK PRIMARY KEY (MEM_NO)
);

COMMENT ON COLUMN MEMBER_LEVEL_TB.MEM_NO IS '회원번호';
COMMENT ON COLUMN MEMBER_LEVEL_TB.MEM_LVL IS '회원레벨';
COMMENT ON COLUMN MEMBER_LEVEL_TB.CK_MEM_EXP IS '누적회원경험치';

-----------------------------------------------------------------------------
-- REVIEW_TB 이용 후기 테이블
DROP TABLE REVIEW_TB;
CREATE TABLE REVIEW_TB (
    REV_SQ NUMBER,
    REV_SCO NUMBER DEFAULT 0 NOT NULL,
    CK_REV_ST VARCHAR2(10) DEFAULT 'PUBLIC' NOT NULL,
    REV_CONT VARCHAR2(255),
    REV_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    RES_CD VARCHAR2(10) NOT NULL,
    
    CONSTRAINT REV_RNO_PK PRIMARY KEY (REV_SQ), 
    CONSTRAINT REV_STATE_CK CHECK(CK_REV_ST IN ('PUBLIC', 'PRIVATE')),
    CONSTRAINT REV_RCD_FK FOREIGN KEY (RES_CD)
        REFERENCES RESERVATION_TB (RES_CD) ON DELETE SET NULL
);

COMMENT ON COLUMN REVIEW_TB.REV_SQ IS '이용후기번호';
COMMENT ON COLUMN REVIEW_TB.REV_SCO IS '평점';
COMMENT ON COLUMN REVIEW_TB.CK_REV_ST IS '공개여부설정';
COMMENT ON COLUMN REVIEW_TB.REV_CONT IS '후기 내용';
COMMENT ON COLUMN REVIEW_TB.REV_REG_DT IS '등록일';
COMMENT ON COLUMN REVIEW_TB.RES_CD IS '예약코드';

DROP SEQUENCE REV_SEQ;
CREATE SEQUENCE REV_SEQ;

-----------------------------------------------------------------------------

-- REVIEW_IMAGE_TB 이용 후기 이미지 테이블
DROP TABLE REVIEW_IMAGE_TB;
CREATE TABLE REVIEW_IMAGE_TB (
    REV_IMG_SQ NUMBER,
    REV_IMG_ORG_NAME VARCHAR2(500) NOT NULL,
    REV_IMG_PATH_NAME VARCHAR2(1000) DEFAULT 'assets/resources/upload/review/' NOT NULL,
    REV_IMG_REG_DT DATE DEFAULT SYSDATE NOT NULL,
    REV_NO NUMBER NOT NULL,
    
    CONSTRAINT REVIMG_INO_PK PRIMARY KEY (REV_IMG_SQ),
    CONSTRAINT REVIMG_RNO_FK FOREIGN KEY (REV_NO)
        REFERENCES REVIEW_TB (REV_SQ) ON DELETE CASCADE
);

COMMENT ON COLUMN REVIEW_IMAGE_TB.REV_IMG_SQ IS '이용후기 이미지번호';
COMMENT ON COLUMN REVIEW_IMAGE_TB.REV_IMG_ORG_NAME IS '이미지 원본 이름';
COMMENT ON COLUMN REVIEW_IMAGE_TB.REV_IMG_PATH_NAME IS '이미지 경로';
COMMENT ON COLUMN REVIEW_IMAGE_TB.REV_IMG_REG_DT IS '등록일';
COMMENT ON COLUMN REVIEW_IMAGE_TB.REV_NO IS '이용후기번호';

DROP SEQUENCE REV_IMG_SEQ;
CREATE SEQUENCE REV_IMG_SEQ;

-----------------------------------------------------------------------------

-- PUBLIC_FACILITIES_TB 공용 시설 테이블
DROP TABLE PUBLIC_FACILITIES_TB;
CREATE TABLE PUBLIC_FACILITIES_TB (
    PUB_FAC_NAME VARCHAR2(30) NOT NULL
);
COMMENT ON COLUMN PUBLIC_FACILITIES_TB.PUB_FAC_NAME IS '공용시설이름';

-- IN_ROOM_FACILITIES_TB 공용 시설 테이블
DROP TABLE IN_ROOM_FACILITIES_TB;
CREATE TABLE IN_ROOM_FACILITIES_TB (
    IN_ROOM_FAC_NAME VARCHAR2(30) NOT NULL
);
COMMENT ON COLUMN IN_ROOM_FACILITIES_TB.IN_ROOM_FAC_NAME IS '객실내시설이름';

-- ETC_FACILITIES_TB 공용 시설 테이블
DROP TABLE ETC_FACILITIES_TB;
CREATE TABLE ETC_FACILITIES_TB (
    ETC_FAC_NAME VARCHAR2(30) NOT NULL
);
COMMENT ON COLUMN ETC_FACILITIES_TB.ETC_FAC_NAME IS '기타시설이름';

-----------------------------------------------------------------------------