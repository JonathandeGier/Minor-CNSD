package nl.jonathandegier.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@ContextConfiguration(classes = Main.class)
//@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringIntegrationTest {
    @Autowired
    protected RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    protected ResponseResult latestResponse = null;

    protected void get(String endpoint) {
        this.latestResponse = restTemplate.execute(getBaseUrl() + endpoint, HttpMethod.GET, null, new ResponseExtractor<ResponseResult>() {
            @Override
            public ResponseResult extractData(ClientHttpResponse clientHttpResponse) throws IOException {
                return new ResponseResult(clientHttpResponse);
            }
        });
    }

    protected void post(String endpoint, Map<String, String> body) {
        this.latestResponse = restTemplate.execute(getBaseUrl() + endpoint, HttpMethod.POST, null, null);
    }

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }
}
