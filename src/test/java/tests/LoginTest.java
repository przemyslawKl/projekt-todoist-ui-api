package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest{

    @Test
    void should_open_todoist_app_test(){
        page.waitForCondition(() -> page.locator("#loading").isHidden(), new Page.WaitForConditionOptions().setTimeout(30000));
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Logowanie"))).isVisible();
    }
}
