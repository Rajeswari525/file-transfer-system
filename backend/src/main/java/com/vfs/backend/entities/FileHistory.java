package com.vfs.backend.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class FileHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_history_seq")
    @SequenceGenerator(name = "file_history_seq", sequenceName = "file_history_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "File name is required")
    @Column(name = "file_name")
    private String fileName;

    @NotEmpty(message = "File path is required")
    @Column(name = "file_path")
    private String filePath;

    @NotNull(message = "processedDateTime is required")
    @Column(name = "processed_date_time")
    private LocalDateTime processedDateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "step_name")
    private String stepName;

    @Column(name = "failure_reason")
    private String failureReason;

}
