package nl.jonathandegier.sqsproducer;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@Profile("aws")
public class QueueConfigAWS {

    @Value("${s3-queue-bucket-name}")
    private String bucketName;

    @Bean
    @Primary
    public AmazonSQS amazonSQSExtendedClient(@Qualifier("defaultSQS") AmazonSQS amazonSQS, ExtendedClientConfiguration configuration) {
        return new AmazonSQSExtendedClient(amazonSQS, configuration);
    }

    @Bean("defaultSQS")
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder.defaultClient();
    }

    @Bean
    @Primary
    public ExtendedClientConfiguration extendedClientConfiguration(AmazonS3 s3Client) {
        return new ExtendedClientConfiguration()
                .withPayloadSupportEnabled(s3Client, bucketName);
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.defaultClient();
    }
}
