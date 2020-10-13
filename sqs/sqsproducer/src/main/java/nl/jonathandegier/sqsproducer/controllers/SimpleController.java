package nl.jonathandegier.sqsproducer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("queue")
public class SimpleController {

    private static final String QUEUE = "first-queue";
    private final QueueMessagingTemplate queue;

    @PostMapping
    public ResponseEntity<String> addToQueue(@RequestBody String body) {
        queue.convertAndSend(QUEUE, body);
        return ResponseEntity.ok("Send message to " + QUEUE + " with body:\n" + body);
    }
}