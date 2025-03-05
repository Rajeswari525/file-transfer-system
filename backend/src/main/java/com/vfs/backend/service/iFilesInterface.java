package com.vfs.backend.service;

import com.vfs.backend.dto.FileDto;

import java.time.LocalDateTime;
import java.util.List;

public interface iFilesInterface {
    public Boolean uploadFile(String fileName, String fileContent);
    public String downloadFile(String fileName, String filePath);
    public List<FileDto> getFileHistory(LocalDateTime start, LocalDateTime end);
    public List<String> getRejectedFiles();
}
