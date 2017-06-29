package selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class AddCarTest {
    private final String START_URL = "http://localhost:8080";
    private final String SIGNIN_LINK_XPATH = "//a[contains(@href,'/signin')]";
    private final String NEWCAR_LINK_XPATH = "//a[contains(@href,'/car/new')]";
    private final String CAR_TYPE_SELECT_ID = "id_car-type";
    private final String LOGIN_ID = "username";
    private final String PASSWORD_ID = "password";
    private final String LOGIN_SUBMIT_XPATH = "//input[@type='submit']";
    private final String RESULT_TEXT = "Регистрация нового пользователя "
            + "проведена успешно";
    private final int START_USER_INDEX = 10;
    private final int END_USER_INDEX = 15;
    private final int CAR_TYPE_COUNT=7;


    @Test
    public void addNewCarTest() {

        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion
                .BEST_SUPPORTED);
        driver.setJavascriptEnabled(true);
        driver.get(START_URL);

        for (int x = START_USER_INDEX; x < END_USER_INDEX; x++) {
            userSignin(driver, x);
            addCars(driver);
        }
        driver.close();
    }

    private void addCars(WebDriver driver) {
        WebElement element = driver.findElement(By.id(CAR_TYPE_SELECT_ID));
        Select select = new Select(element);
        int countCarbyType= new Random().nextInt(3)+1;
        for (int x=0; x<CAR_TYPE_COUNT;x++){
            select.selectByIndex(x);
        }
    }

    private void userSignin(WebDriver driver, int index) {
        WebElement element = driver.findElement(By.xpath(SIGNIN_LINK_XPATH));
        element.click();
        element = driver.findElement(By.id(LOGIN_ID));
        String input = "user" + index;
        element.sendKeys(input);
        element = driver.findElement(By.id(PASSWORD_ID));
        element.sendKeys(input);
        element = driver.findElement(By.xpath(LOGIN_SUBMIT_XPATH));
        element.click();
        element = driver.findElement(By.xpath(NEWCAR_LINK_XPATH));
        element.click();
    }
}
