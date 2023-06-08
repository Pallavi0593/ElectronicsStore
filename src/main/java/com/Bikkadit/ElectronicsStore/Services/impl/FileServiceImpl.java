package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.FileService;
import com.Bikkadit.ElectronicsStore.exceptions.BadApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String UploadImage(MultipartFile file, String path) throws IOException {

        log.info("Request to upload Image");
        String originalFilename = file.getOriginalFilename();

        //To generate Random File name to avoid Same file names
        String randomFileName = UUID.randomUUID().toString();
        //we will get extensions like .png,.jpg from original file And  Add it to random file
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomFileNameWithExtension = randomFileName.concat(extension);
        //file Full path folder to file location
        String fullPath = path+randomFileNameWithExtension;

        log.info("Full Image Path :{}" ,fullPath);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jepg")) {
//Save File
            log.info("File Extension is",extension);
            //if path /folder not existes then create folder
            File folder = new File(path);
            if (!folder.exists()) {

                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPath));
            return randomFileNameWithExtension;
        } else {
            throw new BadApiRequestException("File With this" + extension + "  Not Allowed");
        }


    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {

       String fullPath = path + File.separator + filename;
       InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
