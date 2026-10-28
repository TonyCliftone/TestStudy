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
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.get("http://localhost:8080");

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
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("secret123");
        driver.findElement(By.xpath("//*[@class='primary']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='n-name']")));
        driver.findElement(By.xpath("//*[@id='n-name']")).sendKeys("Свекла");
        driver.findElement(By.xpath("//*[@id='n-price']")).sendKeys("150");
        driver.findElement(By.xpath("//*[@id='add-btn']")).click();
        driver.findElement(By.xpath("//*[@href='/']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, \"product-card\") and @data-name=\"Свекла\"]")));
        Assertions.assertThat(driver.findElement(By.xpath("//*[contains(@class, \"product-card\") and @data-name=\"Свекла\"]")).getAttribute("data-name"))
                .as("В списке товаров есть Свекла")
                .isEqualTo("Свекла");
    }

    @Test
    @Tag("main")
    void checkAddGoodInCartTest(){
        driver.findElement(By.xpath("//*[(@data-action='add-to-cart') and @data-name=\"Свекла\"]")).click();
        driver.findElement(By.xpath("//button[@id=\"open-cart-btn\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='makeOrder']")));
        Assertions.assertThat(driver.findElement(By.tagName("b")).getText())
                .as("Название товара в корзине")
                .isEqualTo("Свекла");
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
        driver.findElement(By.xpath("//*[(@data-action='add-to-cart') and @data-name=\"Свекла\"]")).click();
        driver.findElement(By.xpath("//button[@id=\"open-cart-btn\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='makeOrder']")));
        Assertions.assertThat(driver.findElement(By.tagName("b")).getText())
                .as("Название товара в корзине")
                .isEqualTo("Свекла");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"open-cart-btn\"]")));
        driver.findElement(By.xpath("//button[@id=\"open-cart-btn\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='makeOrder']")));
        Assertions.assertThat(driver.findElement(By.id("total-price")).getText())
                .as("В корзине отсутствуют товары")
                .isEqualTo("0");
    }


@AfterEach
    void tearDown() {
        driver.quit();
}
}
