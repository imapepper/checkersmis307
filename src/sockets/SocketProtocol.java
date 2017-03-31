package sockets;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;

public class SocketProtocol {

    void processInput(String input) {
        JsonObject jsonObject = Json.createReader(new StringReader(input)).readObject();
        String action = jsonObject.getString("action");
        System.out.println(action);
    }
}
