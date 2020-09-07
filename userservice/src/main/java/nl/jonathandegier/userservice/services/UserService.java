package nl.jonathandegier.userservice.services;

import nl.jonathandegier.userservice.clients.UserClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private final Random rand;
    private final UserClient client;

    public UserService(UserClient client) {
        this.rand = new Random();
        this.client = client;
    }

    public String getRandomName() {
        int id = getRandomInt(1, 10);
        String user = client.getUser(id);

        JSONObject obj = new JSONObject(user);
        return obj.getString("name");
    }

    private int getRandomInt(int min, int bound) {
        return this.rand.nextInt(bound) + min;
    }

}
