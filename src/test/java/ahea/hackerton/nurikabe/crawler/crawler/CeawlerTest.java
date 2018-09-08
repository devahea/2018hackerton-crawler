package ahea.hackerton.nurikabe.crawler.crawler;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import org.junit.Test;

public class CeawlerTest {
    @Test
    public void jbrowser() {
        JBrowserDriver driver = new JBrowserDriver(Settings.builder().
            timezone(Timezone.AMERICA_NEWYORK).build());

        // This will block for the page load and any
        // associated AJAX requests
        driver.get("https://www.puzzles-mobile.com/nurikabe/random/15x15-hard");

        // You can get status code unlike other Selenium drivers.
        // It blocks for AJAX requests and page loads after clicks
        // and keyboard events.
        System.out.println(driver.getStatusCode());

        // Returns the page source in its current state, including
        // any DOM updates that occurred after page load
        System.out.println(driver.getPageSource());



        // Close the browser. Allows this thread to terminate.
        driver.quit();

    }
}
