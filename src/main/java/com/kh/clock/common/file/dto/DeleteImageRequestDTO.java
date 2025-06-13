package com.kh.clock.common.file.dto;

import java.util.List;
import lombok.Data;

@Data
public class DeleteImageRequestDTO {
  private List<ImageFileDTO> imageList;
}
