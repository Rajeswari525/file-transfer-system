package com.vfs.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "File name is required")
    @Column(name = "file_name")
    private String fileName;

    @NotEmpty(message = "File path is required")
    @Column(name = "file_path")
    private String filePath;

    @NotEmpty(message = "File size is required")
    @Column(name = "file_size")
    private String fileSize;

    @NotNull(message = "processedStartDateTime is required")
    @Column(name = "processed_start_date_time")
    private LocalDateTime processedStartDateTime;

    @NotNull(message = "processedEndDateTime is required")
    @Column(name = "processed_end_date_time")
    private LocalDateTime processedEndDateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "last_executed_step")
    private String lastExecutedStep;

    @Column(name = "uploaded_by")
    private Long uploadedBy;
}
