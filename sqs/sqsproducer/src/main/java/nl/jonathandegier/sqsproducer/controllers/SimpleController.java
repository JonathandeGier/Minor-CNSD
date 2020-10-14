package nl.jonathandegier.sqsproducer.controllers;

import com.amazonaws.services.sqs.AmazonSQS;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;


@RestController
@AllArgsConstructor
@RequestMapping()
public class SimpleController {

    private static final String QUEUE = "file-queue";

    private final AmazonSQS queue;

    @PostMapping("/upload")
    public ResponseEntity<String> addFileToQueue(@RequestParam("file") MultipartFile file) throws Exception {
        String content = Base64.getEncoder().encodeToString(file.getBytes());
        System.out.println("Sending ...");
        queue.sendMessage(QUEUE, content);
        return ResponseEntity.ok("Send message to " + QUEUE);
    }
}