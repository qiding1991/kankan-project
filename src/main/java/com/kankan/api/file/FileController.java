package com.kankan.api.file;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

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

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation("上传图片，返回图片的id")
    @PostMapping("upload")
    public CommonResponse<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileId = fileService.storeFile(new FileDocument(file));
        String downLoadUrl = generateDownloadUrl(fileId,file.getOriginalFilename());
        return success(downLoadUrl);
    }

    private String generateDownloadUrl(String fileId,String fileName) {
        if (!serverBaseUrl.endsWith("/")) {
            serverBaseUrl = serverBaseUrl + "/";
        }
        return serverBaseUrl + "file/download/"+fileId+"/"+ URLEncoder.encode(fileName);
    }

    @ApiOperation("下载图片")
    @GetMapping("download/{fileId}/{fileName}")
    public void download(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        FileDocument fileDocument = fileService.downFile(fileId);
        response.setContentType(fileDocument.getContentType());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileDocument.getFileName());
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(fileDocument.getFileContent());
            response.flushBuffer();
        } catch (IOException e) {
            log.error("文件下载失败{}", fileId, e);
            throw e;
        }
    }
}
