package com.fts.fileCheck;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fts.fileCheck.dto.KafkaEventDto;
import com.fts.fileCheck.kafka.KafkaConsumerUtil;
import com.fts.fileCheck.kafka.KafkaProducerUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class FileCheckHandler implements RequestHandler<Map<String,String>, Map<String,String>> {

    @Override
    public Map<String,String> handleRequest(Map<String, String> event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Input event is : " + event);

        KafkaConsumerUtil consumer = new KafkaConsumerUtil("start.execution.topic");
        KafkaEventDto kafkaEventDto = consumer.consumeMessage();

        logger.log("Message from Kafka is : " + kafkaEventDto);

        KafkaProducerUtil producer = new KafkaProducerUtil();

//        {
//            "status" : "success",
//            "id" : 432,
//            "fileName" : "sample.txt",
//            "filePath" : "bucketName/IN/sample.txt"
//        }


        //get the filecontent from s3 bucket
        //check if the file content is empty or not
        //if all looks good,
            // update the record in file table with the lastExecuted step
            // insert a record into file_history table
            // send a event to kafka topic for next lambda execution
        //if not,
            // update the record in file table with the error message
            // insert a record into file_history table with the error message
            // send a event to kafka topic to execute cleanup lambda

        String lambdaName = "FileCheckLambda";

        String dbName = System.getenv("DB_NAME");
        String dbUserName = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");
        logger.log("DB_NAME is : " + dbName);
        logger.log("DB_USERNAME is : " + dbUserName);
        logger.log("DB_PASSWORD is : " + dbPassword);

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:5432/" + dbName ,dbUserName, dbPassword);

            String updateFileTable = "update file set last_executed_step = '" + lambdaName + "' where id = " + kafkaEventDto.getId();
            logger.log("Query to update file table is : " + updateFileTable);

            String insertFileHistory = "insert into file_history (file_name, file_path, step_name, status, failure_reason) values ('" + kafkaEventDto.getFileName() + "', '" + kafkaEventDto.getFilePath() + "', '" + lambdaName + "', 'success', '')";
            logger.log("Query to insert into file_history table is : " + insertFileHistory);

            con.prepareStatement(updateFileTable).execute();
            con.prepareStatement(insertFileHistory).execute();

            kafkaEventDto.setStatus("success");
            kafkaEventDto.setPrevStepName(lambdaName);

            producer.sendMessage("start.fileTransfer.topic", kafkaEventDto);

            con.close();
        }
        catch(Exception e) {
            kafkaEventDto.setStatus("failed");
            kafkaEventDto.setFailureReason(e.getMessage());
            kafkaEventDto.setPrevStepName(lambdaName);
            producer.sendMessage("start.cleanUp.topic", kafkaEventDto);
            throw new RuntimeException(e);
        }
        return event;
    }
}
