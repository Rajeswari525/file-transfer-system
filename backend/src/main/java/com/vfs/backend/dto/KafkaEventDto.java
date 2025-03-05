package com.vfs.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEventDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("filePath")
    private String filePath;

    @JsonProperty("status")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("failureReason")
    private String failureReason;
}
