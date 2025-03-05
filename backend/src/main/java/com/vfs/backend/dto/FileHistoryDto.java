package com.vfs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileHistoryDto {

    private Long id;

    private String fileName;

    private String filePath;

    private LocalDateTime processedDateTime;

    private String status;

    private String stepName;

    private String failureReason;
}
