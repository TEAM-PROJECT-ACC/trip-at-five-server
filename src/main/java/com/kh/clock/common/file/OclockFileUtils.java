package com.kh.clock.common.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 타 클래스에서 의존성 주입으로 생성하게 하기 위해 @Component를 사용
 */
@Component
public class OclockFileUtils {
  
  private final Path uploadPath;

  public OclockFileUtils(@Value("${file.upload-dir}") String uploadDir) {
      this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
      try {
        // 
        /** java.nio.file.Files 
         * 
         * JDK 1.7 버전 이후부터 사용 가능함
         * 
         * - 디렉터리 생성
         * 모두 리턴은 Path 형태로 받음
         * 디렉터리나 파일을 생성할때 부가적인 설정도 해줄 수 있음
         * 
         * Files.createDirectory(path);
         *  상위 디렉터리가 존재하지 않을 경우 => NosuchFileException이 발생
         * Files.createDirectories(path);
         *  상위 디렉터리가 존재하지 않을 경우 => 모두 생성함.
         *  접근 권한이 없을경우 => AccessDeniedException이 발생
         *  
         * Files.createFile(path);
         *  상위 디렉터리가 존재하지 않을 경우 => NosuchFileException이 발생
         */
        Files.createDirectories(this.uploadPath);
      } catch (IOException e) {
        System.out.println("OclockFileUtils 예외");
        e.printStackTrace();
      }
  }
  
  /** 파일 저장 메서드
   * 
   * @param file : 저장할 이미지 파일
   * @param subFolder : 하위폴더(이미지 저장 날짜)
   * @param fileName : 저장할 이미지 파일의 변환된 명
   * @param type : 구분. 숙박: acc, 이용후기 : review 등
   */
  public void saveFile(MultipartFile file, String subFolder, String fileName, String type) {
    /** Path 의 resolve 메서드 (절대경로로 처리)
     * 
     * 경로를 연결해주는 메서드
     * 
     * 비슷한것으로 join 메서드가 있음
     * 
     * join 과 resolve 메서드의 차이는 상대경로인지 절대경로인지의 차이다.
     */
    Path targetLocation = this.uploadPath.resolve(type).resolve(subFolder).resolve(fileName);
//    System.out.println("파일 저장 메서드의 파일 저장 경로 : " + targetLocation);
    
    try {
      Files.createDirectories(targetLocation.getParent()); // getParent 메서드 : 현재 경로의 부모 메서드 반환
      file.transferTo(targetLocation.toFile()); // transferTo 메서드 : 업로드된 파일을 지정된 파일 객체에 저장하는 기능을 제공
    } catch (IOException e) {
      System.out.println("파일 저장 실패");
      e.printStackTrace();
    }
  }

  // 파일 명 변경 메서드
  public static String changeFileName(MultipartFile mFile) {
    String originFileName = mFile.getOriginalFilename();
    String ext = originFileName.substring(originFileName.lastIndexOf("."));
    String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    int randomValue = (int)(Math.random() * 99999999) + 1;
    
    return "Trip-at-five-o'clock_" + currentDate + "_" + randomValue + ext;
  }
  
  // 하위 경로 생성 (날짜 폴더)
  public String createFilePath(String type) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    String relativePath = formatter.format(now);
    createFolders(relativePath, type);
    
    return relativePath;
  }

  // 폴더 생성
  private void createFolders(String relativePath, String type) {
    try {
      Path folderPath = uploadPath.resolve(type).resolve(relativePath);
      if (!Files.exists(folderPath)) {
//        System.out.println("폴더 생성! : " + folderPath);
        Files.createDirectories(folderPath);
      }
    } catch (IOException e) {
      throw new RuntimeException("디렉터리 생성 실패: " + relativePath, e);
    }
  }
}
