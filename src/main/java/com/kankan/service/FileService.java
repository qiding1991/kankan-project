package com.kankan.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.kankan.param.file.FileDocument;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;


@Service
public class FileService {

   private MongoTemplate mongoTemplate;

   private GridFsTemplate gridFsTemplate;

   private   GridFSBucket gridFSBucket;


    public FileService(MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate, GridFSBucket gridFSBucket) {
        this.mongoTemplate = mongoTemplate;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFSBucket = gridFSBucket;
    }

    public String storeFile(FileDocument fileDocument) throws IOException {
        //文件基本信息
        if (exists(fileDocument)) {
            return fileDocument.getFileId();
        }
        byte[]fileContent=fileDocument.getFileContent();
        fileDocument.setFileContent(null);

        //存储文件内容
        try(ByteArrayInputStream inputStream=new ByteArrayInputStream(fileContent) ) {
            gridFsTemplate.store(inputStream,fileDocument.getFileId(),fileDocument.getContentType());
        }catch (Exception exception){
            throw new IOException();
        }
        mongoTemplate.insert(fileDocument);
        return fileDocument.getFileId();
    }

    public FileDocument downFile(String fileId) throws IOException {
        Query documentQuery=Query.query(Criteria.where("fileId").is(fileId));
        FileDocument fileDocument=mongoTemplate.findOne(documentQuery,FileDocument.class);

        Query query=Query.query(Criteria.where("filename").is(fileId));
        GridFSFile fsFile= gridFsTemplate.findOne(query);
        GridFSDownloadStream downloadStream= gridFSBucket.openDownloadStream(fsFile.getObjectId());
        if(downloadStream.getGridFSFile().getLength()>0){
            GridFsResource resource=new GridFsResource(fsFile,downloadStream);
            fileDocument.setFileContent(IOUtils.toByteArray(resource.getInputStream()));
        }else {
            fileDocument.setFileContent(new byte[0]);
        }
        return fileDocument;
    }



    public boolean exists(FileDocument fileDocument) {
        Query query = Query.query(Criteria.where("md5").is(fileDocument.getMd5()));
        FileDocument result = mongoTemplate.findOne(query, FileDocument.class);
        Boolean exits = FileDocument.isNotNull(result);
        if (exits) {
            BeanUtils.copyProperties(result, fileDocument);
        }
        return exits;
    }
}
