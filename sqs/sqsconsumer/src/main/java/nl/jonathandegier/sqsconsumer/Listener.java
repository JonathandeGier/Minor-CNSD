package nl.jonathandegier.sqsconsumer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class Listener {

    private static final String QUEUE = "file-queue";
    private static final String BUCKET_UPLOAD = "cnsd-processed-files";
    private static final String TOPIC = "processed-files";

    private final AmazonSQS queue;
    private final AmazonS3 s3;
    private final AmazonSNS sns;

    @Scheduled(fixedRate = 10000)
    public void receiveMessageFromFileQueue() throws IOException {

        ReceiveMessageResult result = queue.receiveMessage(new ReceiveMessageRequest(QUEUE));
        List<Message> messages = result.getMessages();

        for (Message message : messages) {
            log.info("processing big file ...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Completed processing of big file");
            log.info("Converting to file ...");

            File file = new File(message.getMessageId());
            FileUtils.writeByteArrayToFile(file, Base64.getDecoder().decode(message.getBody()));

            var s3Result = s3.putObject(new PutObjectRequest(BUCKET_UPLOAD, message.getMessageId(), file));
            log.info("Uploaded to S3");

            var topicResult = sns.createTopic(TOPIC);
            sns.publish(new PublishRequest(topicResult.getTopicArn(), message.getMessageId()));

            queue.deleteMessage(new DeleteMessageRequest(QUEUE, message.getReceiptHandle()));
        }
    }
}
