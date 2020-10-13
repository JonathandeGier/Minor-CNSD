package nl.jonathandegier.sqsconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Listener {

    private static final String QUEUE = "first-queue";

    @SqsListener(QUEUE)
    public void receiveMessage(String body) {
        log.info("Received message on queue " + QUEUE + " with body:\n" + body);
    }
}
