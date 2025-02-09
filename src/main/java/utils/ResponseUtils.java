package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    public static JsonObject apiResponseToJsonObject(APIResponse response) {
        JsonElement jsonElement = new Gson().fromJson(response.text(), JsonElement.class);
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        } else {
            // Handle the case where the response is not a JSON object
            // For example, you can throw a custom exception or return a default value
            throw new RuntimeException("Expected a JSON object but was a JSON primitive");
        }
    }
    public static JsonArray apiResponseToJsonArray (APIResponse response){
        return new Gson().fromJson(response.text(), JsonArray.class);
    }
}
