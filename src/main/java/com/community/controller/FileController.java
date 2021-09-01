package com.community.controller;

import com.community.dto.FileDTO;
import com.community.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("editormd-image-file");
        String url = fileService.uploadPhotoInQuestion(0, multipartFile);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUrl(url);
        fileDTO.setSuccess(1);
        return fileDTO;
    }
}
