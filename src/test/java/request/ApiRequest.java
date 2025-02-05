package request;

import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class ApiRequest {
    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApiRequest.class);

    public static APIResponse post (APIRequestContext apiContext, String endpoint, JsonObject payload) {
        final var response = apiContext.post(endpoint, RequestOptions.create().setData(payload));
        log.info("API CALL POST - {}", response.text());
        return response;
    }
}
