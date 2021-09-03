package com.community.service;

import com.community.domain.Photo;
import com.community.mapper.PhotoMapper;
import com.community.provider.COSProvider;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {
    @Autowired
    private PhotoMapper photoMapper;


    COSProvider cosProvider = new COSProvider();
    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.region}")
    private String region;
    public String getPhoto(String bucketName,String key){
        COSClient cosClient = cosProvider.begin();
        String url = cosClient.getObjectUrl(bucketName,key).toString();
        return url;
    }

    @Transient
    public void upload(File file){
        COSClient cosClient =  cosProvider.begin();
        String key =  UUID.randomUUID().toString();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }

    public String uploadPhotoInQuestion(int questionId,MultipartFile file){
        COSClient cosClient = cosProvider.begin();
        String originalFilename =  file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.',originalFilename.length()-1));
        String fileName = UUID.randomUUID()+suffix;
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        PutObjectRequest putObjectRequest = new PutObjectRequest("community-1251122888", fileName,inputStream,objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String url = cosClient.getObjectUrl(bucketName,fileName).toString();
        photoMapper.insertPhoto(questionId,url,fileName);
        cosClient.shutdown();
        url = url + "?imageMogr2/thumbnail/800x";
        return url;
    }

    public String getSmallPhoto(String key){
        COSClient cosClient = cosProvider.begin();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName,key);

        return "";
    }
}
