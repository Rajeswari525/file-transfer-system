package com.fts.fileTransfer.dto;

import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("failureReason")
    private String failureReason;

    @JsonProperty("prevStepName")
    private String prevStepName;
}
