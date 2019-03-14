package api;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws IOException {
//        String jsonInput = "{\"name\":\"mkyong\", \"age\":29}";

        File file = new File("/home/NIX/rubinskyi/Desktop/tracker-app/src/main/java/data.txt");

        ObjectMapper jsonMapper = new ObjectMapper();
        TypeReference<Map<String, Map<String, DataType>>> typeRef =
                new TypeReference<Map<String, Map<String, DataType>>>() {};
        Map<String, Map<String, DataType>> configMap = jsonMapper.readValue(file, typeRef);
        System.out.println(configMap);
    }
}
