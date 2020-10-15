package nl.jonathandegier.sqsconsumer;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource(value = "classpath:/secrets.properties")
public class QueueConfig {
    @Value("${aws_access_key_id}")
    private String accessKey;

    @Value("${aws_secret_access_key}")
    private String secretKey;

    @Value("${aws_session_token}")
    private String sessionToken;

    @Value("${s3-bucket-name}")
    private String bucketName;

    private static final Regions REGION = Regions.US_EAST_1;


    @Bean
    @Primary
    public AmazonSQS amazonSQSExtendedClient() {
        return new AmazonSQSExtendedClient(amazonSQS(), extendedClientConfiguration());
    }

    @Bean
    @Primary
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(REGION)
                .withCredentials(credentials())
                .build();
    }

    @Bean
    @Primary
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder
                .standard()
                .withRegion(REGION)
                .withCredentials(credentials())
                .build();
    }

    private AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withRegion(REGION)
                .withCredentials(credentials())
                .build();
    }

    private ExtendedClientConfiguration extendedClientConfiguration() {
        return new ExtendedClientConfiguration()
                .withPayloadSupportEnabled(amazonS3Client(), bucketName);
    }

    private AWSStaticCredentialsProvider credentials() {
        return new AWSStaticCredentialsProvider(new BasicSessionCredentials(accessKey, secretKey, sessionToken));
    }
}
