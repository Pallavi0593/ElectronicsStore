package com.Bikkadit.ElectronicsStore.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    public String  UploadImage(String path, MultipartFile file)throws IOException;

    InputStream getResource(String path, String filename) throws FileNotFoundException;

}
