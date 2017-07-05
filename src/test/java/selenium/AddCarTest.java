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
    private final String DESCRIPTION_ID="id_description";
    private final String DISLOCATION_ID="id_location";
    private final String CAPACITY_ID="id_capacity";
    private final String LOAD_ID="id_load";
    private final String PRICE_ADD_BUTTON_ID="submit-id-new-offer";

    private final String LOGIN_SUBMIT_XPATH = "//input[@type='submit']";

    private final int START_USER_INDEX = 16;
    private final int END_USER_INDEX = 17;
    private final int CAR_TYPE_COUNT=7;
    private final String[] CITIES=new String[]{"dnipro", "kyiv", "poltava"};
    private final String SUBMIT_BUTTON_ID="submit_new_car_id";
    private final String LOGOUT_LINK_XPATH="//a[contains(@href,'/logout')]";


    @Test
    public void addNewCarTest() {

        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion
                .FIREFOX_52);
        driver.setJavascriptEnabled(false);
        driver.get(START_URL);

        for (int x = START_USER_INDEX; x < END_USER_INDEX; x++) {
            userSignin(driver, x);
            addCars(driver, x);
            userLogout(driver);
        }
        driver.close();
    }

    private void userLogout(HtmlUnitDriver driver) {
        System.out.println("userLogout");
        WebElement element = driver.findElement(By.xpath(LOGOUT_LINK_XPATH));
        element.click();
    }

    private void addCars(WebDriver driver, int index) {
        System.out.println("addCars - "+index);
        int countCarbyType= new Random().nextInt(3)+1;
        WebElement element = null;
        for (int x=0; x<CAR_TYPE_COUNT;x++){
            System.out.println("addCars - CAR_TYPE_COUNT - "+x);
            for (int y=0;y<countCarbyType;y++){
                System.out.println("addCars - CAR_TYPE_COUNT - countCarbyType"
                        + " - "+y);
                element = driver.findElement(By.id(CAR_TYPE_SELECT_ID));
                Select select = new Select(element);
                select.selectByIndex(x);
                element = driver.findElement(By.id(DESCRIPTION_ID));
                element.sendKeys(String.format("user%s description",index));
                element = driver.findElement(By.id(DISLOCATION_ID));
                element.sendKeys(String.format(CITIES[new Random().nextInt(3)
                        ]));
                element = driver.findElement(By.id(CAPACITY_ID));
                element.sendKeys(""+(new Random().nextInt(10)+1));
                element = driver.findElement(By.id(LOAD_ID));
                element.sendKeys(""+(new Random().nextInt(10)+1));
                for (int i=0;i<2;i++) {
                    System.out.println("addCars - new price - "+i);
                    element = driver.findElement(By.id(PRICE_ADD_BUTTON_ID));
                    element.click();
                    element = driver.findElement(By.id(String
                            .format("offers%s.price",i)));
                    element.sendKeys("" + (new Random().nextInt(10) + 1));
                    element = driver.findElement(By.id(String
                            .format("offers%s.description",i)));
                    element.sendKeys(String.format("offer %s description", i));
                }
                element = driver.findElement(By.id(SUBMIT_BUTTON_ID));
                element.click();
            }
        }
    }

    private void userSignin(WebDriver driver, int index) {
        System.out.println("userSignin - "+index);
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
