package nl.jonathandegier.sqsconsumer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/secrets.properties")
public class QueueConfig {
    @Value("${aws_access_key_id}")
    private String accessKey;

    @Value("${aws_secret_access_key}")
    private String secretKey;

    @Value("${aws_session_token}")
    private String sessionToken;

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQS());
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQS() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                                new BasicSessionCredentials(accessKey, secretKey, sessionToken)
                        )
                ).build();
    }
}
