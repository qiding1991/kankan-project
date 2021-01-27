package com.kankan.api.file;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.param.file.FileDocument;
import com.kankan.service.FileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api(tags = "文件操作")
@Log4j2
@RestController
@RequestMapping("file")
public class FileController extends BaseController {

  private FileService fileService;

  @Value("${fileServer.baseUrl}")
  private String serverBaseUrl;

  @Value("${fileServer.ffmpeg-path}")
  private String ffmpegPath;

  @Value("${fileServer.fast-path}")
  private String fastPath;

  @Value("${fileServer.result-path}")
  private String resultPath;

  @Value("${fileServer.shell-path}")
  private String shellPath;


  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @ApiOperation("上传图片，返回图片的id")
  @PostMapping("upload")
  public CommonResponse<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
    String fileId = fileService.storeFile(new FileDocument(file));
    String downLoadUrl = generateDownloadUrl(fileId, file.getOriginalFilename());
    return success(downLoadUrl);
  }

  private String generateDownloadUrl(String fileId, String fileName) {
    if (!serverBaseUrl.endsWith("/")) {
      serverBaseUrl = serverBaseUrl + "/";
    }
    return serverBaseUrl + "file/download/" + fileId + "/" + URLEncoder.encode(fileName);
  }

//
//  private String generateVideUrl(String fileId) {
//    if (!serverBaseUrl.endsWith("/")) {
//      serverBaseUrl = serverBaseUrl + "/";
//    }
//    return serverBaseUrl + "file/download/video/" + fileId;
//  }


  @ApiOperation("下载资源")
  @GetMapping("download/{fileId}/{fileName}")
  public void download(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
    FileDocument fileDocument = fileService.downFile(fileId);
    response.setHeader("Content-Type",fileDocument.getContentType());
    try (OutputStream outputStream = response.getOutputStream()) {
      outputStream.write(fileDocument.getFileContent());
      response.flushBuffer();
    } catch (IOException e) {
      log.error("文件下载失败{}", fileId, e);
      throw e;
    }
  }

//  @ApiOperation("下载视频")
//  @GetMapping("download/video/{fileId}")
//  public void downloadVideo(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
//    String filePath = resultPath + fileId;
//    log.info("downLoadPath={}", filePath);
//    File file = new File(filePath);
//    response.setContentType("video/mp4");
//    try (OutputStream outputStream = response.getOutputStream()) {
//      InputStream inputStream = new FileInputStream(file);
//      byte[] bytes = new byte[200];
//      while (inputStream.read(bytes) != -1) {
//        outputStream.write(bytes);
//      }
//    } catch (IOException e) {
//      log.error("文件下载失败{}", fileId, e);
//      throw e;
//    }
//  }


  @ApiOperation("上传视频")
  @PostMapping("upload/video")
  public CommonResponse<String> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
    //1生成随机文件名

    String fileName = file.getOriginalFilename();
    String suffixName = fileName.substring(fileName.indexOf("."));
    //2保存到指定目录
    String fileId = UUID.randomUUID().toString().replace("-", "");
    final String realFileName = fileId + suffixName;

    String ffmpegFileName = ffmpegPath + realFileName;
    String fastStartFileName = fastPath + realFileName;
    String resultFileName = resultPath + realFileName;
    File storePath = new File(ffmpegFileName);
    file.transferTo(storePath);
    //3.调用本地脚本
    StringBuilder command = new StringBuilder(shellPath)
      .append(" ").append(ffmpegFileName)
      .append(" ").append(fastStartFileName)
      .append(" ").append(resultFileName);
    log.info("执行的命令={}", command);
    Process process = Runtime.getRuntime().exec(command.toString());
    process.waitFor();
    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    log.info("执行成功，response={}", sb.toString());
    //4.读取结果文件
    File resultFile = new File(resultPath + realFileName);
    String  storeFileId= fileService.storeFile(new FileDocument(resultFile,file));
    String downLoadUrl = generateDownloadUrl(storeFileId,file.getOriginalFilename());
    //5.存入到本地库
    return success(downLoadUrl);
  }

}
