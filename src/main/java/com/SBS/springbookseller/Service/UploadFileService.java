package com.SBS.springbookseller.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface UploadFileService {


    void init();

    public void uploadFile(MultipartFile file) throws IOException;

    public Resource load(String fileName);

    public Stream<Path> loadAll();





}
