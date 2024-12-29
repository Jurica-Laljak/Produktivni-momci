package hr.unizg.fer.ticket4ticket.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SeleniumTests {

    @Autowired
    private WebDriver webDriver;


    @BeforeEach
    public void setUp() {
        webDriver.get("http://localhost:8080/");
    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void loadingPageTest() {
        String title = webDriver.getTitle();
        List<WebElement> images = webDriver.findElements(By.tagName("img"));

        Assertions.assertFalse(images.isEmpty());

        for (WebElement image : images) {
            String src = image.getAttribute("src");
            Assertions.assertTrue(src != null && !src.isEmpty());

            Boolean imageLoaded = (Boolean) ((JavascriptExecutor) webDriver).executeScript(
                    "return arguments[0].complete && " +
                    "typeof arguments[0].naturalWidth != 'undefined' && " +
                    "arguments[0].naturalWidth > 0;",
                    image
            );

            Assertions.assertTrue(imageLoaded);
        }

        Assertions.assertEquals(title, "Ticket4Ticket");
    }

    @Test
    public void searchOglasTest() {
        WebElement searchBox = webDriver.findElement(By.tagName("input"));

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        searchBox.sendKeys("tay", Keys.ENTER);

        List<WebElement> oglasi = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("izvodaci")));

        Assertions.assertFalse(oglasi.isEmpty());

        for (WebElement oglas : oglasi) {
            String oglasText = oglas.getText();

            Assertions.assertTrue(oglasText != null && !oglasText.isEmpty());
            Assertions.assertTrue(oglasText.contains("Taylor Swift"));
        }
    }
}
