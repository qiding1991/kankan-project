package com.kankan.param.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import com.kankan.util.Md5Util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("file-info")
public class FileDocument {
  @Id
  private String fileId;
  private String fileName;
  private String contentType;
  private Long fileSize;
  private String md5;
  private byte[] fileContent;
  private Long createTime = Instant.now().toEpochMilli();

  public FileDocument(MultipartFile file) throws IOException {
    this.fileId = UUID.randomUUID().toString();
    this.contentType = file.getContentType();
    this.fileName = file.getOriginalFilename();
    this.fileSize = file.getSize();
    this.fileContent = file.getBytes();
    this.md5 = Md5Util.md5Hex(this.fileContent);
  }

  public FileDocument(File file, MultipartFile originFile) throws IOException {
    byte[] fileBytes = IOUtils.toByteArray(new FileInputStream(file));
    this.fileId = UUID.randomUUID().toString();
    this.contentType = originFile.getContentType();
    this.fileName = originFile.getOriginalFilename();
    this.fileSize = Long.valueOf(fileBytes.length);
    this.fileContent = fileBytes;
    this.md5 = Md5Util.md5Hex(fileBytes);
  }


  public static boolean isNotNull(FileDocument fileDocument) {
    return fileDocument != null && fileDocument.fileId != null;
  }
}
