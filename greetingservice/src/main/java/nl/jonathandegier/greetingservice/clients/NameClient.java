package nl.jonathandegier.greetingservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user-service")
public interface NameClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    String getName();
}
