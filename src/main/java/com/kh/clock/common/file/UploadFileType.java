package com.kh.clock.common.file;

/**
 * 파일 업로드 중간 폴더명 유형
 * 
 * ACC: 숙박업소
 * ROOM: 객실
 * REIVEW: 이용후기
 */
public enum UploadFileType {
  /**
   * 열거형으로 선언된 순서에 따라 0 부터 인덱스 값을 갖고 순차적으로 증가함.
   * 열거형 변수들을 선언 후 세미콜론을 찍지 않지만 다른 값과 연결할 경우 찍어야함.
   * 
   * ACC => "acc"로 연결
   */
  ACC("acc"),
  ROOM("acc/room"),
  REIVEW("review");
  
  private final String path;
  
  /**
   * private 으로 다른 클래스에서 생성자 접근을 막아서
   * 새로운 인스턴스를 생성해서 동적으로 변경 막기 
   * 
   * @param path : 열거형 값
   */
  private UploadFileType(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return path;
  }
}
