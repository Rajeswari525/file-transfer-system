package com.vfs.backend.controller;

import com.vfs.backend.dto.ErrorResponseDto;
import com.vfs.backend.dto.FileDto;
import com.vfs.backend.dto.ResponseDto;
import com.vfs.backend.service.iFilesInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/fts", produces = "application/json")
@AllArgsConstructor
@Slf4j
public class FilesController {
    private iFilesInterface filesInterface;

    @PutMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam String fileName, @RequestParam String fileContent){
        log.info("uploading file");
        boolean status = filesInterface.uploadFile(fileName, fileContent);
        if(status){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("File uploaded successfully", "200"));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("File upload failed", "400"));
        }
    }

    @PostMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam String fileName, @RequestParam String filePath){
        log.info("downloading file");
        String url = filesInterface.downloadFile(fileName, filePath);
        return ResponseEntity.status(HttpStatus.OK).body(url);
    }

    @GetMapping("/getExecutions")
    public ResponseEntity<List<FileDto>> getExecutions(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){ //return list of filedto's
        log.info("getting file executions history");
        List<FileDto> fileHistory = filesInterface.getFileHistory(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(fileHistory);
    }

    @GetMapping("/getRejectedFiles")
    public ResponseEntity<List<String>> getRejectedFiles(){ //return a list of rejected files in s3 bucket for re execution
        log.info("getting rejected files details");
        List<String> rejectedFilesList = filesInterface.getRejectedFiles();
        return ResponseEntity.status(HttpStatus.OK).body(rejectedFilesList);
    }

}
