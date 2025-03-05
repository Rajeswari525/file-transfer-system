package com.vfs.backend.service.Implementation;

import com.vfs.backend.dto.FileDto;
import com.vfs.backend.dto.KafkaEventDto;
import com.vfs.backend.entities.File;
import com.vfs.backend.exception.ResourceNotFoundException;
import com.vfs.backend.kafka.KafkaProducerUtil;
import com.vfs.backend.repository.FilesRepository;
import com.vfs.backend.service.iFilesInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FilesImpl implements iFilesInterface {

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private KafkaProducerUtil producer;

    @Override
    public Boolean uploadFile(String fileName, String fileContent) {
        File newFile = new File();
        newFile.setFileName(fileName);
        newFile.setFileSize("50Kb");
        newFile.setFilePath("testBucket/IN/sample.txt");
        newFile.setProcessedStartDateTime(LocalDateTime.now());
        newFile.setProcessedEndDateTime(LocalDateTime.now());
        newFile.setStatus("processing");
        newFile.setLastExecutedStep("cleanUp");
        newFile.setUploadedBy(1234L);
        log.info("New File Record is "+newFile);
        newFile = filesRepository.save(newFile);
        KafkaEventDto kafkaEventDto = new KafkaEventDto();
        kafkaEventDto.setId(newFile.getId());
        kafkaEventDto.setFileName(newFile.getFileName());
        kafkaEventDto.setFilePath(newFile.getFilePath());
        kafkaEventDto.setStatus(newFile.getFileSize());
        producer.sendMessage("start.execution.topic", kafkaEventDto);
        return true;
    }

    @Override
    public String downloadFile(String fileName, String filePath) {
        log.info("File name received is "+fileName);
        File fileFound = filesRepository.findByFileName(fileName)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find file details with the given filename"));
        log.info("File found is "+fileFound);
        return fileFound.getFilePath();
    }

    @Override
    public List<FileDto> getFileHistory(LocalDateTime start, LocalDateTime end) {
        return List.of(new FileDto(),new FileDto());
    }

    @Override
    public List<String> getRejectedFiles() {
        return List.of("file1","file2");
    }
}
