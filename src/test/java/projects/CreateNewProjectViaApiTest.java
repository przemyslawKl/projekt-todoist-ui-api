package projects;

import api.request.ApiRequest;
import api.steps.ApiSteps;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import utils.FirstAndLastNameUtils;
import utils.ResponseUtils;


public class CreateNewProjectViaApiTest extends BaseTest {

    private String projectId;
    @AfterEach
    void cleaningAfterEachTest(){
        log.info("Deleting project with id {}", projectId);
        ApiRequest.delete(apiContext, "projects/" + projectId);
        log.info("==============================================================");
    }
    @Test
    void should_create_project_test(){
        //GIVEN
        final var projectName = "Project " + FirstAndLastNameUtils.getFirstName() + " " + FirstAndLastNameUtils.getLastName();
        //WHEN
        final var response = ApiSteps.createProject(apiContext, projectName);
        projectId = ResponseUtils.apiResponseToJsonObject(response).get("id").getAsString();
        //THEN
        PlaywrightAssertions.assertThat(page.locator("//a[contains(@aria-label, '" + projectName + "')]")).isVisible();
    }

    @Test
    void should_create_project_test_with_one_letter(){
        //GIVEN
        final var projectName = "X";
        //WHEN
        final var response = ApiSteps.createProject(apiContext, projectName);
        projectId = ResponseUtils.apiResponseToJsonObject(response).get("id").getAsString();
        //THEN
        PlaywrightAssertions.assertThat(page.locator("//a[contains(@aria-label, '" + projectName + "')]")).isVisible();

    }
    //@Test
    void should_create_five_projects_test_with_one_letter(){
        //GIVEN
        int i = 0;
        while (true) {
            final var projectName = "Project " + FirstAndLastNameUtils.getFirstName() + " " + FirstAndLastNameUtils.getLastName();
            try {
                //WHEN
                final var response = ApiSteps.createProject(apiContext, projectName);

                projectId = ResponseUtils.apiResponseToJsonObject(response).get("id").getAsString();
                //THEN
                PlaywrightAssertions.assertThat(page.locator("//a[contains(@aria-label, '" + projectName + "')]")).isVisible();
                i++;
            } catch (RuntimeException e) {
                if (e.getMessage().contains("Maximum number of projects per user limit reached")) {
                    log.info("Project limit reached after creating {} projects", i);
                    break;
                } else {
                    throw e;
                }
            }
        }
    }
}
