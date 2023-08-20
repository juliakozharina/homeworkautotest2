import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHW {
    private WebDriver driver;
    private final static String LOGIN = "juliako13@mail.ru";
    private final static String PASSWORD = "1234Qwerty12345&";
    private static final String NAME = "Юлия";
    private static final String LATIN_NAME = "Julia";
    private static final String SURNAME = "Иванова";
    private static final String LATIN_SURNAME = "Ivanova";
    private static final String DATE_OF_BIRTH = "01.01.1990";
    private static final String COUNTRY = "Россия";
    private static final String CITY = "Москва";
    private static final String ENGLISH = "Начальный уровень (Beginner)";
    private static final String PHONE_NUMBER = "+7 908 123-45-67";
    private static final String NAME_FIELD = "//input[@name=\"fname\"]";
    private static final String LATIN_NAME_FIELD = "//input[@name=\"fname_latin\"]";
    private static final String SURNAME_FIELD = "//input[@name=\"lname\"]";
    private static final String LATIN_SURNAME_FIELD = "//input[@name=\"lname_latin\"]";
    private static final String BLOG_NAME_FIELD = "//input[@name=\"blog_name\"]";
    private static final String DATE_OF_BIRTH_FIELD = "//input[@name=\"date_of_birth\"]";
    private static final String COUNTRY_FIELD = "(//div[@class=\"input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation\"])[1]";
    private static final String CITY_FIELD = "(//div[@class=\"input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation\"])[2]";
    private static final String ENGLISH_FIELD = "(//div[@class=\"input input_full lk-cv-block__input lk-cv-block__input_fake lk-cv-block__input_select-fake js-custom-select-presentation\"])[3]";
    private static final String FULL_DAY_CHECKBOX = "(//span[@class=\"checkbox__label lk-cv-block__text lk-cv-block__text_work-schedule\"])[1]";
    private static final String REMOTE_CHECKBOX = "(//span[@class=\"checkbox__label lk-cv-block__text lk-cv-block__text_work-schedule\"])[3]";

    @BeforeAll
    public static void webDriverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void someTest() {
        fillData();
        checkData();
    }

    private void auth() {
        click("//button[@class=\"sc-mrx253-0 enxKCy sc-945rct-0 iOoJwQ\"]");
        clearAndEnter(By.xpath("(//input[@required])[1]"), LOGIN);
        clearAndEnter(By.xpath("(//input[@required])[2]"), PASSWORD);
        click("//button[@class=\"sc-9a4spb-0 gYNtqF sc-11ptd2v-2-Component cElCrZ\"]");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"sc-199a3eq-0 fJMWHf\"]")));
    }

    private void fillData() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //        Открыть https://otus.ru
        getHomePage();
//        Авторизоваться на сайте
        auth();
//        Войти в личный кабинет
        getAboutYourselfPage();
//        В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        clearAndEnter(By.xpath(NAME_FIELD), NAME);
        clearAndEnter(By.xpath(LATIN_NAME_FIELD), LATIN_NAME);
        clearAndEnter(By.xpath(SURNAME_FIELD), SURNAME);
        clearAndEnter(By.xpath(LATIN_SURNAME_FIELD), LATIN_SURNAME);
        clearAndEnter(By.xpath(BLOG_NAME_FIELD), NAME);
        clearAndEnter(By.xpath(DATE_OF_BIRTH_FIELD), DATE_OF_BIRTH);
        click(COUNTRY_FIELD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title=\"Россия\"]")));
        click("//button[@title=\"Россия\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CITY_FIELD)));
        click(CITY_FIELD);
        click("//button[@title=\"Москва\"]");
        click(ENGLISH_FIELD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title=\"Начальный уровень (Beginner)\"]")));
        click("//button[@title=\"Начальный уровень (Beginner)\"]");
        click(FULL_DAY_CHECKBOX);
        click(REMOTE_CHECKBOX);
        click("//button[@class=\"js-change-phone input-group__addon button\"]");
        clearAndEnter(By.xpath("//input[@placeholder=\"Номер телефона\"]"), PHONE_NUMBER);
        click("//button[@class=\"js-send button button_blue button_md\"]");
        click("//div[@class=\"modal__close ic-close js-close-modal\"]");
        click("//button[@title=\"Сохранить и продолжить\"]");
    }

    private void checkData() {
        driver.quit();
        driver = new ChromeDriver();
        getHomePage();
        auth();
        getAboutYourselfPage();
        checkValue(NAME_FIELD, NAME);
        checkValue(LATIN_NAME_FIELD, LATIN_NAME);
        checkValue(SURNAME_FIELD, SURNAME);
        checkValue(LATIN_SURNAME_FIELD, LATIN_SURNAME);
        checkValue(BLOG_NAME_FIELD, NAME);
        checkValue(DATE_OF_BIRTH_FIELD, DATE_OF_BIRTH);
        checkText(COUNTRY_FIELD, COUNTRY);
        checkText(CITY_FIELD, CITY);
        checkText(ENGLISH_FIELD, ENGLISH);
        checkCheckbox(FULL_DAY_CHECKBOX);
        checkCheckbox(REMOTE_CHECKBOX);
        checkValue("(//input[@name=\"email\"])[1]", LOGIN);
        checkValue("(//input[@name=\"phone\"])[1]", PHONE_NUMBER);
    }

    private void getAboutYourselfPage() {
        WebDriverWait wait = new WebDriverWait(driver, 1);
        driver.get("https://otus.ru/lk/biography/personal/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NAME_FIELD)));
    }

    private void getHomePage() {
        driver.get("https://otus.ru");
    }

    private void clearAndEnter(By by, String text) {
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    private void click(String xPath) {
        WebElement element = driver.findElement(By.xpath(xPath));
        if (element.isDisplayed()){
            element.click();
        }
    }

    private void checkValue(String xPath, String data) {
        WebElement element = driver.findElement(By.xpath(xPath));
        String actual = element.getAttribute("value");
        System.out.println(actual);
        assertThat(actual).isEqualTo(data);
    }

    private void checkText(String xPath, String data) {
        WebElement element = driver.findElement(By.xpath(xPath));
        String actual = element.getText();
        System.out.println(actual);
        assertThat(actual).isEqualTo(data);
    }

    private void checkCheckbox(String xPath) {
        WebElement element = driver.findElement(By.xpath(xPath));
        boolean actual = element.isEnabled();
        assertThat(actual).isTrue();
    }
}
