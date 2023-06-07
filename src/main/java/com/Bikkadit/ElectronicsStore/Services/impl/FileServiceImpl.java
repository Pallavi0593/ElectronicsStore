package com.Bikkadit.ElectronicsStore.Services.impl;

import com.Bikkadit.ElectronicsStore.Services.FileService;
import com.Bikkadit.ElectronicsStore.exceptions.BadApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String UploadImage( MultipartFile file,String path) throws IOException {

        log.info("");
      String originalFilename = file.getOriginalFilename();
        log.info("");
        //To generate Random File name to avoid Same file names
        String randomFileName = UUID.randomUUID().toString();
        //we will get extensions like .png,.jpg from original file And  Add it to random file
       String extension= originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomFileNameWithExtension = randomFileName.concat(extension);
        //file Full path folder to file location
        String fullPath= path + File.separator + randomFileNameWithExtension;

        if(extension.equalsIgnoreCase("png")|| extension.equalsIgnoreCase("jpg")||extension.equalsIgnoreCase("jepg"))
        {
//Save File
            //if path /folder not existes then create folder
File folder=new File(path);
if(!folder.exists()) {
    folder.mkdir();
}
            Files.copy(file.getInputStream(), Paths.get(fullPath));
return  fullPath;
        }else {
            throw new BadApiRequest("File With this"+extension+"Not Allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        return null;
    }
}
