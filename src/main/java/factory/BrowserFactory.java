package factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import utils.Properties;

public class BrowserFactory {

    private Playwright pw;

    private Browser browser;

    public Playwright getPw(){
        if (pw == null) {
            pw = Playwright.create();
        }
        return pw;
    }
    public Browser getBrowser(){
        String browserName = Properties.getProperty("browser.channel");
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(Properties.getProperty("browser.headless")))
                .setSlowMo(Integer.parseInt(Properties.getProperty("browser.slow.motion")));

        switch (browserName) {
            case "chromium" -> browser = getPw().chromium().launch(launchOptions);
            case "firefox" -> browser = getPw().firefox().launch(launchOptions);
            case "webkit" -> browser = getPw().webkit().launch(launchOptions);
            case "chrome" -> browser = getPw().chromium().launch(launchOptions.setChannel("chrome"));
            case "msedge" -> browser = getPw().chromium().launch(launchOptions.setChannel("msedge"));
            default -> throw new IllegalStateException("Browser " + browserName + " is not supported");
        }
        return browser;
    }
}
