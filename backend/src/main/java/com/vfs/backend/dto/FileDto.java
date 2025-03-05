package com.vfs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private Long id;

    private String fileName;

    private String filePath;

    private int fileSize;

    private LocalDateTime processedStartDateTime;

    private LocalDateTime processedEndDateTime;

    private String status;

    private String lastExecutedStep;

    private String uploadedBy;

    private FileHistoryDto fileHistoryDto;
}
