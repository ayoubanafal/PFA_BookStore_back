package com.SBS.springbookseller.Controller;


import com.SBS.springbookseller.Service.UploadFileService;
import com.SBS.springbookseller.UploadFiles.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Images")
public class UploadController {

    @Autowired
    UploadFileService uploadFileService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            uploadFileService.uploadFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage());
        }
    }


    @GetMapping("/getFiles")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = uploadFileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(UploadController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }



    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = uploadFileService.load(filename);
        try {
            return ResponseEntity.ok()
                    .body(file);
        }catch (Exception e){
            throw new RuntimeException("Error");
        }
    }

}
