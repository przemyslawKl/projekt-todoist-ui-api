package api.steps;

import api.request.ApiRequest;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.assertj.core.api.Assertions;
import utils.FirstAndLastNameUtils;

import static utils.ResponseUtils.apiResponseToJsonObject;

public class ApiSteps {

    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApiSteps.class);

    public static APIResponse createProject (APIRequestContext apiContext, String projectName) {
        //GIVEN
        JsonObject payload = new JsonObject();
        payload.addProperty("name", projectName);
        //WHEN
        final var APIResponse = ApiRequest.post(apiContext, "projects", payload);
        log.info("Created new project with id {}", apiResponseToJsonObject(APIResponse).get("id").getAsString());
        //ASSERT
        PlaywrightAssertions.assertThat(APIResponse).isOK();
        Assertions.assertThat(apiResponseToJsonObject(APIResponse).get("id").getAsString()).isNotNull();
        Assertions.assertThat(apiResponseToJsonObject(APIResponse).get("name").getAsString()).isEqualTo(projectName);
        return APIResponse;
    }
}
