package nl.jonathandegier.greetingservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
public interface NameClient {

    @RequestMapping("/user")
    String getName();
}
