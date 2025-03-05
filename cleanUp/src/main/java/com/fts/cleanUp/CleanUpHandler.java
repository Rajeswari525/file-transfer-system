package com.fts.cleanUp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fts.cleanUp.dto.KafkaEventDto;
import com.fts.cleanUp.kafka.KafkaConsumerUtil;
import com.fts.cleanUp.kafka.KafkaProducerUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class CleanUpHandler implements RequestHandler<Map<String,String>, Map<String,String>> {
    @Override
    public Map<String, String> handleRequest(Map<String, String> event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Input event is : " + event);
        KafkaConsumerUtil consumer = new KafkaConsumerUtil("start.cleanUp.topic");
        KafkaEventDto kafkaEventDto = consumer.consumeMessage();

        logger.log("Message from Kafka is : " + kafkaEventDto);



        KafkaProducerUtil producer = new KafkaProducerUtil();

//        {
//            "status" : "success",
//            "id" : 432,
//            "fileName" : "sample.txt",
//            "filePath" : "bucketName/IN/sample.txt"
//        }

        //move the file to processed folder if status in event is success
            //if file moved successfully,
                // update the record in file table with the lastExecuted step
                // insert a record into file_history table
            //if not,
                //throw runtime exception
        //else move to rejected folder
            //if file moved successfully,
                // update the record in file table with the lastExecuted step and failure_reason
                // insert a record into file_history table with the failure reason
            //if not,
                //throw runtime exception


        String lambdaName = "CleanUpLambda";
        String dbName = System.getenv("DB_NAME");
        String dbUserName = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");
        logger.log("DB_NAME is : " + dbName);
        logger.log("DB_USERNAME is : " + dbUserName);
        logger.log("DB_PASSWORD is : " + dbPassword);

        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:5432/" + dbName ,dbUserName, dbPassword);

            String updateFileTable = "update file set last_executed_step = '" + lambdaName + "' where id = " + event.get("id");
            logger.log("Query to update file table is : " + updateFileTable);

            String insertFileHistory = "insert into file_history (file_name, file_path, step_name, status, failure_reason) values ('" + event.get("fileName") + "', '" + event.get("filePath") + "', '" + lambdaName + "', 'success', '')";
            logger.log("Query to insert into file_history table is : " + insertFileHistory);

            con.prepareStatement(updateFileTable).execute();
            con.prepareStatement(insertFileHistory).execute();

            event.put("status", "success");
            event.put("filePath", "finalPathOfFile");
            event.put("prevStep", lambdaName);

            con.close();

        } catch (Exception e) {
            event.put("status","failed");
            event.put("errorMessage",e.getMessage());
            event.put("prevStep", lambdaName);
            throw new RuntimeException(e);
        }
        return event;
    }
}
