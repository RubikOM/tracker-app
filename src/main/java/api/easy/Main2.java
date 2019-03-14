package api.easy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main2 {
    public static void main(String[] args) throws IOException {
        String jsonInput = "{\n" +
                "    \"isEmpty\": false,\n" +
                "    \"translation\": {\n" +
                "        \"heading\": \"threshold\",\n" +
                "        \"lingvoTranslations\": \"порог\",\n" +
                "        \"lingvoSoundFileName\": \"threshold.wav\",\n" +
                "        \"socialTranslations\": \"\",\n" +
                "        \"lingvoDictionaryName\": \"LingvoUniversal (En-Ru)\",\n" +
                "        \"type\": \"ExactWord\",\n" +
                "        \"srcLangId\": 0,\n" +
                "        \"dstLangId\": 0,\n" +
                "        \"source\": \"Undefined\"\n" +
                "    },\n" +
                "    \"sourceLanguageId\": 1033,\n" +
                "    \"targetLanguageId\": 1049,\n" +
                "    \"seeAlso\": []\n" +
                "}";

        Map<String, Object> response = new ObjectMapper().readValue(jsonInput, HashMap.class);
        Map<String, String> translation = (Map<String, String>) response.get("translation");
        String result = translation.get("lingvoTranslations");
        System.out.println(result);
    }
}
