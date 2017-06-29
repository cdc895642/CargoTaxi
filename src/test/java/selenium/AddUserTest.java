package selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AddUserTest {

    private final String START_URL = "http://localhost:8080";
    private final String SIGNUP_LINK_XPATH = "//a[contains(@href,'/signup')]";
    private final String LOGIN_ID = "id_login";
    private final String EMAIL_ID = "id_email";
    private final String NEW_PHONE_BUTTON_ID = "submit-id-new-phone";
    private final String NEW_PHONE_ID = "phones0.number";
    private final String PASSWORD_ID = "id_password";
    private final String PASSWORD_MATCH_ID = "id_matching-password";
    private final String SUBMIT_ID = "submit-id-signup";
    private final String RESULT_TEXT = "Регистрация нового пользователя "
            + "проведена успешно";
    private final int START_USER_INDEX=10;
    private final int END_USER_INDEX=15;


    @Test
    public void addNewUserTest() {

        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion
                .BEST_SUPPORTED);
        driver.setJavascriptEnabled(true);
        driver.get(START_URL);

        for (int x = START_USER_INDEX; x < END_USER_INDEX; x++) {
            WebElement element = driver.findElement(By.xpath(SIGNUP_LINK_XPATH));
            element.click();
            element = driver.findElement(By.id(LOGIN_ID));
            String input="user" + x;
            element.sendKeys(input);
            element = driver.findElement(By.id(EMAIL_ID));
            input="user" + x + "@gmail.com";
            element.sendKeys(input);
            element = driver.findElement(By.id(NEW_PHONE_BUTTON_ID));
            element.click();
            element = driver.findElement(By.id(NEW_PHONE_ID));
            input="+3806700000" + x;
            element.sendKeys(input);
            element = driver.findElement(By.id(PASSWORD_ID));
            input="user" + x;
            element.sendKeys(input);
            element = driver.findElement(By.id(PASSWORD_MATCH_ID));
            input="user" + x;
            element.sendKeys(input);
            element = driver.findElement(By.id(SUBMIT_ID));
            element.click();
            driver.findElement(By.xpath("//*[contains(text(), '" + RESULT_TEXT + "')]"));
        }
        driver.close();
    }
}
