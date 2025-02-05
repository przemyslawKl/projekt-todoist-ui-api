package projects;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.RequestOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import request.ApiRequest;
import tests.BaseTest;
import utils.FirstAndLastNameUtils;
import utils.ResponseUtils;

import static utils.ResponseUtils.*;

public class CreateNewProjectViaApiTest extends BaseTest {

    @Test
    void should_create_project_test(){
        //GIVEN
        JsonObject payload = new JsonObject();
        final var projectName = "Project " + FirstAndLastNameUtils.getFirstName() + " " + FirstAndLastNameUtils.getLastName();
        payload.addProperty("name", projectName);
        //WHEN
        final var APIResponse = ApiRequest.post(apiContext, "projects", payload);
        PlaywrightAssertions.assertThat(APIResponse).isOK();
        Assertions.assertThat(apiResponseToJsonObject(APIResponse).get("id").getAsString()).isNotNull();
        Assertions.assertThat(apiResponseToJsonObject(APIResponse).get("name").getAsString()).isEqualTo(projectName);
        //THEN
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(projectName))).isVisible();
    }
}
