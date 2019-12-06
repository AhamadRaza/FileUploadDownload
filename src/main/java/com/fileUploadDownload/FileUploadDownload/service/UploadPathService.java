package com.fileUploadDownload.FileUploadDownload.service;

import java.io.File;

public interface UploadPathService {
    public File getFilePath(String modifiedFileName, String path);
}