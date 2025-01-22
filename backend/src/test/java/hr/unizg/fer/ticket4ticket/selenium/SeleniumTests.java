package hr.unizg.fer.ticket4ticket.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(value = {"classpath:skripta.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTests {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private Environment env;

    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        webDriver.get("http://localhost:8080/");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    @Order(1)
    public void createUserAndChangePreferencesTest() {
        loginWithOauth(env.getProperty("test.email1"), env.getProperty("test.pass1"), false);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("genre-name"))).stream().limit(5).forEach(WebElement::click);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("submit-button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("btn-outline-primary"))).getFirst().click();

        WebElement imeKorisnika = webDriver.findElement(By.cssSelector("input#imeKorisnika"));

        imeKorisnika.click();
        imeKorisnika.clear();
        imeKorisnika.sendKeys("Prvi");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-outline-success"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert1 = webDriver.switchTo().alert();
        String text1 = alert1.getText();
        Assertions.assertEquals(text1, "imeKorisnika je uspješno izmijenjen.");
        alert1.accept();
        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("btn-outline-primary"))).get(1).click();

        WebElement prezimeKorisnika = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#prezimeKorisnika")));

        prezimeKorisnika.click();
        prezimeKorisnika.clear();
        prezimeKorisnika.sendKeys("Korisnik");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-outline-success"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert2 = webDriver.switchTo().alert();
        String text2 = alert2.getText();
        Assertions.assertEquals(text2, "prezimeKorisnika je uspješno izmijenjen.");
        alert2.accept();
        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("btn-outline-primary"))).getLast().click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("genre-name"))).stream().limit(2).forEach(WebElement::click);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("submit-button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#allow-notifications"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#allow-notifications"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary"))).click();
    }

    @Test
    @Order(2)
    public void loginAndCreateTicketAdTest() {
        loginWithOauth(env.getProperty("test.email1"), env.getProperty("test.pass1"), false);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/userUlaznice']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-ticket-btn"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=text]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=text]"))).sendKeys("FAM_ZA_009");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=text]"))).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close-btn"))).click();

        WebElement ticket = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ticket")));

        Assertions.assertTrue(ticket.isDisplayed());
        Assertions.assertTrue(ticket.getText().contains("FAM_ZA_009"));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-create-ad"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("select"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("option[value^='{\"idUlaznice\":19']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-create-ad[type=button]"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        String text = alert.getText();
        Assertions.assertEquals(text, "Oglas je uspješno kreiran za ulaznicu: FAM_ZA_009");
        alert.accept();
        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/userOglasi']"))).click();

        WebElement oglas1 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("section"))).get(2).findElement(By.className("header-container"));

        Assertions.assertTrue(oglas1.isDisplayed());
        Assertions.assertTrue(oglas1.getText().contains("Moj oglas #1"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary"))).click();

        WebElement oglas2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("1")));

        Assertions.assertTrue(oglas2.isDisplayed());
        Assertions.assertTrue(oglas2.getText().contains("Prvi Korisnik"));
    }

    @Test
    @Order(3)
    public void createAnotherUserAndSwitchTicketTest() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("1"))).click();

        List<WebElement> gumbZaRazmjenu = webDriver.findElements(By.className("button"));
        Assertions.assertTrue(gumbZaRazmjenu.isEmpty());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close-details"))).click();

        loginWithOauth(env.getProperty("test.email2"), env.getProperty("test.pass2"), false);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("genre-name"))).stream().limit(3).forEach(WebElement::click);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("submit-button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("btn-outline-primary"))).getFirst().click();

        WebElement imeKorisnika = webDriver.findElement(By.cssSelector("input#imeKorisnika"));

        imeKorisnika.click();
        imeKorisnika.clear();
        imeKorisnika.sendKeys("Drugi");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-outline-success"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        webDriver.switchTo().alert().accept();
        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("btn-outline-primary"))).get(1).click();

        WebElement prezimeKorisnika = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#prezimeKorisnika")));

        prezimeKorisnika.click();
        prezimeKorisnika.clear();
        prezimeKorisnika.sendKeys("Korisnik");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-outline-success"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        webDriver.switchTo().alert().accept();
        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/userUlaznice']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-ticket-btn"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=text]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=text]"))).sendKeys("PREM_NG_002");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=text]"))).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close-btn"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("input"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("input"))).sendKeys("zagreb");
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("input"))).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("custom-card"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("select"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("option[value^='{\"idUlaznice\":2']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-create-ad[type=button]"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        String text = alert.getText();
        Assertions.assertEquals(text, "Uspješno ponuđena ulaznica: PREM_NG_002");
        alert.accept();
        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close-details"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/userOglasi']"))).click();

        WebElement ponuda1 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("section"))).get(1).findElement(By.className("header-container"));
        Assertions.assertTrue(ponuda1.getText().contains("Moja ponuda #1"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-log-out"))).click();

        loginWithOauth(env.getProperty("test.email1"), env.getProperty("test.pass1"), true);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/notifications']"))).click();

        WebElement obavijest = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("obavijest")));
        Assertions.assertTrue(obavijest.getText().contains("Drugi Korisnik"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div/div/div[1]/div/button[2]"))).click();

        WebElement oglas1 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("section"))).getFirst().findElement(By.className("header-container"));
        Assertions.assertTrue(oglas1.getText().contains("Moj oglas #1"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div/section[1]/div/div/div[2]/button[1]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/userUlaznice']"))).click();

        WebElement ticket1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ticket")));
        Assertions.assertTrue(ticket1.getText().contains("PREM_NG_002"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-log-out"))).click();

        loginWithOauth(env.getProperty("test.email2"), env.getProperty("test.pass2"), true);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/userUlaznice']"))).click();

        WebElement ticket2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ticket")));
        Assertions.assertTrue(ticket2.getText().contains("FAM_ZA_009"));
    }

    @Test
    @Order(4)
    public void adminFunctionalityTest() {
        loginWithOauth(env.getProperty("test.email1"), env.getProperty("test.pass1"), false);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/user']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/admin']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-submit-admin"))).click();

        List<WebElement> korisnici = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("report-container")));

        Assertions.assertFalse(korisnici.isEmpty());
        Assertions.assertTrue(korisnici.getFirst().getText().contains("Prvi Korisnik"));
        Assertions.assertTrue(korisnici.getLast().getText().contains("Drugi Korisnik"));

        korisnici.getFirst().findElement(By.className("korisnik-info")).click();
        WebElement idTransakcije = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.table-row")));

        Assertions.assertTrue(idTransakcije.getText().contains("1"));
        Assertions.assertTrue(idTransakcije.getText().contains("Provedena"));

        korisnici.getLast().findElement(By.cssSelector("button.btn-grant-admin")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-autosuggest__input"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-autosuggest__input"))).sendKeys("Drugi");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-autosuggest__input"))).sendKeys(Keys.ARROW_DOWN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-autosuggest__input"))).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-autosuggest__input"))).sendKeys(Keys.ENTER);

        WebElement drugi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("report-container")));

        Assertions.assertTrue(drugi.getText().contains("Korisnik je administrator"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-delete-korisnik"))).click();

        Alert alert = webDriver.switchTo().alert();
        String text = alert.getText();
        Assertions.assertTrue(text.contains("Jeste li sigurni da želite obrisati korisnika Drugi Korisnik?"));
        alert.accept();

        webDriver.switchTo().defaultContent();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-submit-admin"))).click();

        Assertions.assertEquals(1, wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("report-container"))).size());
    }

    private void loginWithOauth(String mail, String pass, Boolean anotherUser) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("google"))).click();

        int size = 0;
        Logger logger = LoggerFactory.getLogger(SeleniumTests.class);

        if (anotherUser) {
            List<WebElement> opcije = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='link']")));
            size = opcije.size();
            logger.warn("velicina: " + size);
            if (opcije.getLast().isDisplayed())
                opcije.getLast().click();
        }

        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));

        email.sendKeys(mail);
        email.sendKeys(Keys.ENTER);

        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=password]")));

        password.click();
        password.sendKeys(pass);
        password.sendKeys(Keys.ENTER);

        if (size <= 2)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class$='tyoyWc']"))).click();
    }
}
