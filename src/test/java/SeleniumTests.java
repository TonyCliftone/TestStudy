import config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
            void setup(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(TestConfig.ELEMENT_TIMEOUT));
        driver.get(TestConfig.STAND_URL);

    }

@Test
@Tag("main")
    void driverTest() {
    driver.findElement(By.xpath("//*[@data-action='qty-change'][@data-step='1'][@data-id='1']")).click();
    Assertions.assertThat(driver.findElement(By.id("q-1")).getAttribute("value"))
            .as("Поле ввода количества должно быть равно 2")
            .isEqualTo("2");
}

    @Test
    @Tag("main")
    void checkAddGoodAndVisibleTest(){
        driver.findElement(By.xpath("//*[@href='/admin']")).click();
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys(TestConfig.ADMIN_USERNAME);
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(TestConfig.ADMIN_PASSWORD);
        driver.findElement(By.xpath("//*[@class='primary']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='n-name']")));
        driver.findElement(By.xpath("//*[@id='n-name']")).sendKeys(TestConfig.START_PRODUCT_NAME);
        driver.findElement(By.xpath("//*[@id='n-price']")).sendKeys(TestConfig.START_PRODUCT_PRICE);
        driver.findElement(By.xpath("//*[@id='add-btn']")).click();
        driver.findElement(By.xpath("//*[@href='/']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, \"product-card\") and @data-name=\"" + TestConfig.START_PRODUCT_NAME + "\"]")));
        Assertions.assertThat(driver.findElement(By.xpath("//*[contains(@class, \"product-card\") and @data-name=\"" + TestConfig.START_PRODUCT_NAME + "\"]")).getAttribute("data-name"))
                .as("В списке товаров есть " + TestConfig.START_PRODUCT_NAME)
                .isEqualTo(TestConfig.START_PRODUCT_NAME);
    }

    @Test
    @Tag("main")
    void checkAddGoodInCartTest(){
        driver.findElement(By.xpath("//*[(@data-action='add-to-cart') and @data-name=\"" + TestConfig.START_PRODUCT_NAME + "\"]")).click();
        driver.findElement(By.xpath("//button[@id=\"open-cart-btn\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='makeOrder']")));
        Assertions.assertThat(driver.findElement(By.tagName("b")).getText())
                .as("Название товара в корзине")
                .isEqualTo(TestConfig.START_PRODUCT_NAME);
    }

    @Test
    @Tag("main")
    void checkWrongLoginTest(){
        driver.findElement(By.xpath("//*[@href='/admin']")).click();
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys("aaaadmin");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("ssssecret123");
        driver.findElement(By.xpath("//*[@class='primary']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='alert alert-danger']")));
        Assertions.assertThat(driver.findElement(By.xpath("//*[@class='alert alert-danger']")).getText())
                .as("Отображается алерт Неверные учетные данные пользователя")
                .isEqualTo("Неверные учетные данные пользователя");
    }

    @Test
    @Tag("main")
    void checkCartAfterRefreshTest(){
        driver.findElement(By.xpath("//*[(@data-action='add-to-cart') and @data-name=\"" + TestConfig.START_PRODUCT_NAME + "\"]")).click();
        driver.findElement(By.xpath("//button[@id=\"open-cart-btn\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='makeOrder']")));
        Assertions.assertThat(driver.findElement(By.tagName("b")).getText())
                .as("Название товара в корзине")
                .isEqualTo(TestConfig.START_PRODUCT_NAME);
        String totalBefore = driver.findElement(By.id("total-price")).getText(); // добавлена фиксация того что было в корзине до обновления (по стоимости)
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@id='open-cart-btn']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='makeOrder']")));
        Assertions.assertThat(driver.findElement(By.id("total-price")).getText()) // сравнение того что было в корзине и то что после обновления в корзине (по стоимости)
                .as("Корзина должна сохраняться после обновления страницы")
                .isEqualTo(totalBefore);
    }


@AfterEach
    void tearDown() {
        driver.quit();
}
}
