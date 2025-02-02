package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import factory.BrowserFactory;
import org.junit.jupiter.api.*;
import utils.Properties;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.Properties.getProperty;
import static utils.StringUtils.removeRoundBrackets;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaseTest.class);

    private BrowserFactory browserFactory;
    protected Browser browser;
    protected BrowserContext uiContext;
    protected Page page;

    @BeforeAll
    void beforeAll() {
        browserFactory = new BrowserFactory();
        browser = browserFactory.getBrowser();
    }

    @BeforeEach
    void beforeEach() {
        uiContext = browser.newContext();
        if (isTraceEnabled()) {
            uiContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }
        page = uiContext.newPage();
        page.setViewportSize(Properties.getViewportDimension("browser.width"), Properties.getViewportDimension("browser.height"));
        page.navigate(getProperty("app.url"));
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
