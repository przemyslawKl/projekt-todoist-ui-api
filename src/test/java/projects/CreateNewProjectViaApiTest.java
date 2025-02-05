package projects;

import com.google.gson.JsonObject;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.RequestOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import utils.FirstAndLastNameUtils;
import utils.ResponseUtils;

import static utils.ResponseUtils.*;

public class CreateNewProjectViaApiTest extends BaseTest {

    @Test
    void should_create_project_test(){
        JsonObject payload = new JsonObject();
        final var projectName = "Project " + FirstAndLastNameUtils.getFirstName() + " " + FirstAndLastNameUtils.getLastName();
        payload.addProperty("name", projectName);
        final var APIResponse = apiContext.post("projects", RequestOptions.create().setData(payload));

        PlaywrightAssertions.assertThat(APIResponse).isOK();
        Assertions.assertThat(apiResponseToJsonObject(APIResponse).get("id").getAsString()).isNotNull();
        Assertions.assertThat(apiResponseToJsonObject(APIResponse).get("name").getAsString()).isEqualTo(projectName);
    }
}
