package nl.jonathandegier.userservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jsonPlaceholder", url = "https://jsonplaceholder.typicode.com/")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    String getUser(@PathVariable("id") long id);

}
