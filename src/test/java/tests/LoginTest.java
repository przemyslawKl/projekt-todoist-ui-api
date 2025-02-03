package tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;
import static utils.Properties.*;

public class LoginTest extends BaseTest{

    @Test
    void should_login_to_todoist_app_test(){
        //page.waitForCondition(() -> page.locator("#loading").isHidden(), new Page.WaitForConditionOptions().setTimeout(30000));
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Logowanie"))).isVisible();
        //page.pause();
        page.locator("#element-0").pressSequentially(getProperty("login.email"));
        page.locator("#element-2").pressSequentially(getProperty("login.password"));
        page.locator("button span").click();
        assertThat(page.locator("div [data-testid=app-sidebar-scrollable-container]")).isVisible();

        uiContext.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(getProperty("login.storage.file"))));
    }
}
