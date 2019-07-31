package com.example.doupimage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    //method for uploading single file
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        String folderPath = Paths.get(".").toAbsolutePath().normalize().toString() + File.separator + "uploads";

        if (!(new File(folderPath).exists())) {
            new File(folderPath).mkdir();
        }

        String fileName = file.getOriginalFilename();
        fileName = String.valueOf((new Date()).getTime()) + fileName.substring(fileName.lastIndexOf("."));

        File uploadedFile = new File(folderPath, fileName);

        try {
            uploadedFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }


    //method for downloading file
    @GetMapping(value = "/downloadFile/{fileName:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletResponse response) {

        String filePath = Paths.get(".").toAbsolutePath().normalize().toString() + File.separator + "uploads" + File.separator + fileName;
        Path path = Paths.get(filePath);
        Resource resource = null;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

            // return new ResponseEntity<Object>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("File Not Found ", HttpStatus.OK);
        }
    }

}