package com.kh.clock.common.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileDTO {
  private int no; // 번호 (숙박업소/객실/이용후기)
  private String hashcode; // 이미지 파일 해시코드
  private String imagePath; // 이미지 저장 경로
}
