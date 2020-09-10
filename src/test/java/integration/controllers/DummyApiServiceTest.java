package integration.controllers;

import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Header;

import java.util.concurrent.TimeUnit;

import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class DummyApiServiceTest {
    private void createDummyApiMock() {
        new MockServerClient("dummy.restapiexample.com", 1080)
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/api/v1/employees"),
                exactly(1) // verify the request is only done once
            ).respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("Content-Type", "application/json; charset=utf-8"),
                    new Header("Cache-Control", "public, max-age=86400"))
                .withBody("{\"status\":\"success\",\"data\":[{\"id\":1,\"employee_name\":\"Tiger Nixon\",\"employee_salary\":320800,\"employee_age\":61,\"profile_image\":\"\"},\"message\":\"Successfully! All records has been fetched.\"}")
                .withDelay(TimeUnit.SECONDS, 1) // time for response to come back
        );
    }

    @Test
    public void testGetExternalEmployees() {
//        createDummyApiMock();
    }
}
