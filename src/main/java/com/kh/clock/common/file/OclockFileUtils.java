package com.kh.clock.common.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 타 클래스에서 의존성 주입으로 생성하게 하기 위해 @Component를 사용
 */
@Component
public class OclockFileUtils {
  @Value("${file.dir}")
  private String staticFilePath;

  @Value("${algorithm}")
  private String algorithm;
  
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
    if(file == null || file.isEmpty()) {
      System.out.println("file이 null이거나 없음");
      return;
    }
    
    if(fileName == null || fileName.trim().isEmpty()) {
      System.out.println("파일이름이 null이거나 없음");
    }
    
    /** Path 의 resolve 메서드 (절대경로로 처리)
     * 
     * 경로를 연결해주는 메서드
     * 
     * 비슷한것으로 join 메서드가 있음
     * 
     * join 과 resolve 메서드의 차이는 상대경로인지 절대경로인지의 차이다.
     */
//    Path targetLocation = this.uploadPath.resolve(type).resolve(subFolder).resolve(fileName);
//    System.out.println("파일 저장 메서드의 파일 저장 경로 : " + targetLocation);
//    
//    try {
//      Files.createDirectories(targetLocation.getParent()); // getParent 메서드 : 현재 경로의 부모 메서드 반환
////      System.out.println(targetLocation.toFile());
//      file.transferTo(targetLocation.toFile()); // transferTo 메서드 : 업로드된 파일을 지정된 파일 객체에 저장하는 기능을 제공
//    } catch (IOException e) {
//      System.out.println("파일 저장 실패");
//      e.printStackTrace();
//    }
    Path targetLocation = this.uploadPath.resolve(type).resolve(subFolder).resolve(fileName);
    try {
        Files.createDirectories(targetLocation.getParent());

        // 기존 transferTo 대신 안전하게 byte 복사
        FileCopyUtils.copy(file.getBytes(), targetLocation.toFile());
        System.out.println("파일 저장 성공: " + targetLocation);

    } catch (IOException e) {
        System.out.println("파일 저장 실패");
        e.printStackTrace();
    }
  }

  // 파일 명 변경 메서드
  public static String changeFileName(MultipartFile mFile) {
    String originFileName = mFile.getOriginalFilename();
//    System.out.println("originFileName : " + originFileName);
    String ext = originFileName.substring(originFileName.lastIndexOf("."));
    String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    int randomValue = (int)(Math.random() * 99999999) + 1;
    
    return "Trip-at-five-oclock_" + currentDate + "_" + randomValue + ext;
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
  
  // 임시 폴더 생성
  public Path createTempFolder() {
    Path folderPath = uploadPath.resolve("temp");
    try {
      if (!Files.exists(folderPath)) {
        System.out.println("폴더 생성! : " + folderPath); // 생성 완료
        Files.createDirectories(folderPath);
        
      }
    } catch (IOException e) {
      throw new RuntimeException("디렉터리 생성 실패: ", e);
    }

    return folderPath;
  }
  
  // 파일 처리
  public List<String> saveRoomImage(List<MultipartFile> newImageList, String typePath) {
    String middlePath = UploadFileType.ROOM.getPath(); // 중간 폴더 경로
    String dateFolderPath = createFilePath(middlePath);
    
    List<String> fileUrls = new ArrayList<>();
 
    for (int i = 0; i < newImageList.size(); i++) {
      System.out.println("file : " + newImageList.get(i).getOriginalFilename());
      String fileName = OclockFileUtils.changeFileName(newImageList.get(i));
      
      System.out.println("newImageList.get(i) : " + newImageList.get(i));
      System.out.println("dateFolderPath : " + dateFolderPath);
      System.out.println("fileName : " + fileName);
      System.out.println("typePath ： " + typePath);
      
      saveFile(newImageList.get(i), dateFolderPath, fileName, typePath);
      
      String fileUrl = staticFilePath + middlePath + "/" + dateFolderPath + "/" + fileName; // DB에 저장할 파일 경로
      
      System.out.println("fileUrl : " + fileUrl);
      
      fileUrls.add(fileUrl); // DB에 저장할 값을 리스트에 담기
    }
    
    return fileUrls;
  }
  
  // 파일 임시 처리 (해쉬코드를 위해서)
  public List<String> getHashCodeList(MultipartFile[] imageList) {
    Path tempFolderPath = createTempFolder();
    System.out.println("tempFolderPath : " + tempFolderPath);

    List<String> hashCodeList = new ArrayList<>();
    for(MultipartFile image : imageList) {
      String fileUrl = tempFolderPath + "\\" + image.getOriginalFilename();
      
      saveFile(image, "", image.getOriginalFilename(), "temp");
      
      String hashCode = getHashValue(fileUrl);
      System.out.println("hashCode : " + hashCode);
      
      hashCodeList.add(hashCode);
    }
    
    
    return hashCodeList;
  }
  
  private String getHashValue(String fileName) {
    System.out.println("fileName : " + fileName);
    String hashValue = "";
    try {
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] buffer = new byte[16384];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        
        byte[] hash = digest.digest();

        // 해시 값을 16진수 문자열로 변환
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        System.out.println(algorithm + " 해시 값: " + hexString.toString());
        hashValue = hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        System.err.println("알고리즘을 찾을 수 없습니다: " + e.getMessage());
    } catch (IOException e) {
        System.err.println("파일 입출력 오류: " + e.getMessage());
    }
    return hashValue;
  }

  public void deleteTempFolder(List<MultipartFile> newImageList) {
    Path folderPath = uploadPath.resolve("temp");
    
    try (Stream<Path> paths = Files.list(folderPath)) {
      paths.forEach(path -> {
        System.out.println(path.getFileName()); // 정상출력 확인 완료
      });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    try {
      Files.deleteIfExists(folderPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
