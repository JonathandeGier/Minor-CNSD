package nl.jonathandegier.sqsproducer;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource(value = "classpath:/secrets.properties")
@Profile("dev")
public class QueueConfig {
    @Value("${aws_access_key_id}")
    private String accessKey;

    @Value("${aws_secret_access_key}")
    private String secretKey;

    @Value("${aws_session_token}")
    private String sessionToken;

    @Value("${s3-bucket-name}")
    private String bucketName;


    @Bean
    @Primary
    public AmazonSQS amazonSQSExtendedClient() {
        return new AmazonSQSExtendedClient(amazonSQS(), extendedClientConfiguration());
    }

    private AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(credentials())
                .build();
    }

    private ExtendedClientConfiguration extendedClientConfiguration() {
        return new ExtendedClientConfiguration()
                .withPayloadSupportEnabled(amazonS3Client(), bucketName);
    }

    private AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(credentials())
                .build();
    }

    private AWSStaticCredentialsProvider credentials() {
        return new AWSStaticCredentialsProvider(new BasicSessionCredentials(accessKey, secretKey, sessionToken));
    }
}
