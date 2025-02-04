package tests;

import com.microsoft.playwright.*;
import factory.BrowserFactory;
import org.junit.jupiter.api.*;
import utils.Properties;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static utils.Properties.getProperty;
import static utils.StringUtils.removeRoundBrackets;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaseTest.class);

    private BrowserFactory browserFactory;
    protected Browser browser;
    protected BrowserContext uiContext;
    protected Page page;
    protected APIRequestContext apiContext;

    @BeforeAll
    void beforeAll() {
        browserFactory = new BrowserFactory();
        browser = browserFactory.getBrowser();
    }

    @BeforeEach
    void beforeEach() {
        uiContext = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get(getProperty("login.storage.file"))));
        //uiContext = browser.newContext();

        if (isTraceEnabled()) {
            uiContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }
        page = uiContext.newPage();
        page.setViewportSize(Properties.getViewportDimension("browser.width"), Properties.getViewportDimension("browser.height"));
        page.navigate(getProperty("app.url"));
        Map <String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer" +  getProperty("api.token"));
        apiContext = browserFactory.getPw().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://api.todoist.com/rest/v2/")
                .setExtraHTTPHeaders(headers));
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        if(isTraceEnabled()) {
            String traceName = "traces/trace_"
                    + removeRoundBrackets(testInfo.getDisplayName())
                    + "_" + LocalDateTime.now().format(DateTimeFormatter
                    .ofPattern(getProperty("tracing.date.format"))) + ".zip";
            uiContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(traceName)));
        }
        apiContext.dispose();
        uiContext.close();
    }

    @AfterAll
    void afterAll() {
        browserFactory.getPw().close();
    }

    private boolean isTraceEnabled() {
        return Boolean.parseBoolean(getProperty("tracing.enabled"));
    }
}
