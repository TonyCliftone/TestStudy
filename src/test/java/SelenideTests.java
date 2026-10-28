import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class SelenideTests {

    // Главная страница
    SelenideElement qtyPlusButton = $x("//*[@data-action='qty-change'][@data-step='1'][@data-id='1']");
    SelenideElement qtyInputForProduct1 = $x("//*[@id='q-1']");

    // Переход в админку и авторизация
    SelenideElement adminLink = $x("//*[@href='/admin']");
    SelenideElement usernameInput = $x("//*[@id='username']");
    SelenideElement passwordInput = $x("//*[@id='password']");
    SelenideElement signInButton = $x("//*[@class='primary']");
    SelenideElement errorAlert = $x("//*[@class='alert alert-danger']");

    // Добавление товара в админке
    SelenideElement goodNameInput = $x("//*[@id='n-name']");
    SelenideElement goodPriceInput = $x("//*[@id='n-price']");
    SelenideElement addGoodButton = $x("//*[@id='add-btn']");
    SelenideElement homeLink = $x("//*[@href='/']");
    SelenideElement sveklaProductCard = $x("//*[contains(@class, 'product-card') and @data-name='Свекла']");

    // Корзина
    SelenideElement addSveklaToCartButton = $x("//*[(@data-action='add-to-cart') and @data-name='Свекла']");
    SelenideElement addSirokToCartButton = $x("//*[(@data-action='add-to-cart') and @data-name='сырок']");
    SelenideElement addGranatToCartButton = $x("//*[(@data-action='add-to-cart') and @data-name='гранат']");
    SelenideElement openCartButton = $x("//*[@id='open-cart-btn']");
    SelenideElement makeOrderButton = $x("//*[@id='makeOrder']");
    SelenideElement cartItemName = $x("//div[@id='cart-items']//div[contains(@class, 'cart-item')]//b");
    SelenideElement totalPrice = $x("//*[@id='total-price']");
    SelenideElement goodInProcessing = $x("//div[@id='toast-container']//div[contains(@class, 'toast')][contains(., 'Заказ принят в обработку!')]");
    SelenideElement goodAddedNotification = $x("//div[@id='toast-container']//div[contains(@class, 'toast')][contains(., 'Товар успешно добавлен!')]");

    // Редактирование товара в админке
    SelenideElement pomidorNameInput = $x("//input[starts-with(@id, 'nm-') and @value='Помидор']");
    SelenideElement pomidorPriceInput = $x("//tr[.//input[@value='Помидор']]//input[starts-with(@id, 'pr-')]");
    SelenideElement updatePomidorButton = $x("//tr[.//input[@value='Помидор']]//button[@data-action='update']");
    SelenideElement goodUpdatedNotification = $x("//div[@id='toast-container']//div[contains(@class, 'toast')][contains(., 'обновлен')]");

    // Проверка на главной для 3.4
    SelenideElement pomidorProductCard = $x("//*[contains(@class, 'product-card') and @data-name='Помидор']");
    SelenideElement peretsProductCard = $x("//*[contains(@class, 'product-card') and @data-name='Перец']");

    // Удаление через админку через UI
    SelenideElement deletePeretsButton = $x("//tr[.//input[@value='Перец']]//button[@data-action='delete']");
    SelenideElement goodDeletedNotification = $x("//div[@id='toast-container']//div[contains(@class, 'toast')][contains(., 'Товар удален')]");

    @BeforeEach
    void setup() {
        Configuration.timeout = 3000;
        open("http://localhost:8080");
    }

    private final RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .log(LogDetail.ALL)
            .addQueryParam("page", 0)
            .addQueryParam("size", "10000")
            .build();

    private Integer createdGoodId;

    @Test
    @Tag("main")
    void driverTest() {
        qtyPlusButton.click();
        qtyInputForProduct1
                .as("Поле ввода количества должно быть равно 2")
                .shouldHave(value("2"));
    }

    @Test
    @Tag("main")
    void checkAddGoodAndVisibleTest() {
        adminLink.click();
        usernameInput.setValue("admin");
        passwordInput.setValue("secret123");
        signInButton.click();
        goodNameInput.shouldBe(visible).setValue("Свекла");
        goodPriceInput.setValue("150");
        addGoodButton.click();
        homeLink.click();
        sveklaProductCard
                .shouldBe(visible)
                .as("В списке товаров есть Свекла")
                .shouldHave(attribute("data-name", "Свекла"));
    }

    @Test
    @Tag("main")
    void checkAddGoodInCartTest() {
        addSveklaToCartButton.click();
        openCartButton.click();
        makeOrderButton.shouldBe(visible);
        cartItemName
                .as("Название товара в корзине")
                .shouldHave(text("Свекла"));
    }

    @Test
    @Tag("main")
    void checkWrongLoginTest() {
        adminLink.click();
        usernameInput.setValue("aaaadmin");
        passwordInput.setValue("ssssecret123");
        signInButton.click();
        errorAlert
                .shouldBe(visible)
                .as("Отображается алерт Неверные учетные данные пользователя")
                .shouldHave(text("Неверные учетные данные пользователя"));
    }

    @Test
    @Tag("main")
    void checkCartAfterRefreshTest() {
        addSveklaToCartButton.click();
        openCartButton.click();
        makeOrderButton.shouldBe(visible);
        cartItemName
                .as("Название товара в корзине")
                .shouldHave(text("Свекла"));

        Selenide.refresh();
        openCartButton.shouldBe(visible).click();
        makeOrderButton.shouldBe(visible);
        totalPrice
                .as("В корзине отсутствуют товары")
                .shouldHave(text("0"));
    }

    @Test
    @Tag("main")
    void checkJsAlertAfterAddingTest() {
        addSveklaToCartButton.click();
        addSveklaToCartButton.click();
        addSveklaToCartButton.click();
        openCartButton.click();
        makeOrderButton.click();
        sleep(3000);
        Alert activeAlert = Selenide.switchTo().alert();
        System.out.println(activeAlert.getText());
        sleep(3000);
        activeAlert.accept();

    }

    @Test
    @Tag("main")
    void checkOrderThreeItemsTest() {
        // создание товара через API
        String jsonBody = """
            {
                "name": "сырок",
                "price": 50.0
            }
            """;

        createdGoodId = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");

        // перезагружаем страницу так как мы добавили сырок через апишку после того как в бефорич запустили браузер и страницу с товарами (обновить список)
        Selenide.refresh();

        // добавление трёх сырков в корзину и оформление
        addSirokToCartButton.click();
        addSirokToCartButton.click();
        addSirokToCartButton.click();
        openCartButton.click();
        makeOrderButton.shouldBe(visible).click();
        goodInProcessing.shouldBe(visible);
    }

    @Test
    @Tag("main")
    void checkCartTotalForDifferentGoodsTest() {
        // создание граната через API
        String jsonBody = """
            {
                "name": "гранат",
                "price": 12.0
            }
            """;

        createdGoodId = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");

        // перезагружаем страницу так как мы добавили сырок через апишку после того как в бефорич запустили браузер и страницу с товарами (обновить список)
        Selenide.refresh();

        // фиксируем цену свеклы
        sveklaProductCard.shouldBe(visible);
        double sveklaPrice = Double.parseDouble(
                Objects.requireNonNull(sveklaProductCard.getAttribute("data-price")));

        addGranatToCartButton.click();
        addGranatToCartButton.click();

        addSveklaToCartButton.click();

        openCartButton.click();
        totalPrice.shouldBe(visible);

        double expectedTotal = 12.0 * 2 + sveklaPrice;
        double actualTotal = Double.parseDouble(totalPrice.getText());

        assertThat(actualTotal)
                .as("Общая стоимость товаров в корзине")
                .isEqualTo(expectedTotal, within(0.001));
    }

    @Test
    @Tag("main")
    void checkAddGoodNotificationInAdminTest() {
        adminLink.click();
        usernameInput.setValue("admin");
        passwordInput.setValue("secret123");
        signInButton.click();

        goodNameInput.shouldBe(visible).setValue("Товар из админки");
        goodPriceInput.setValue("77");
        addGoodButton.click();

        // проверяем уведомление
        goodAddedNotification.shouldBe(visible);

    }

    @Test
    @Tag("main")
    void checkEditGoodInAdminTest() {
        adminLink.click();
        usernameInput.setValue("admin");
        passwordInput.setValue("secret123");
        signInButton.click();

        // создаём помидор
        goodNameInput.shouldBe(visible).setValue("Помидор");
        goodPriceInput.setValue("10");
        addGoodButton.click();
        goodAddedNotification.shouldBe(visible);

        // редактируем на перец
        pomidorNameInput.shouldBe(visible).setValue("Перец");
        pomidorPriceInput.setValue("25");
        updatePomidorButton.click();
        goodUpdatedNotification.shouldBe(visible);
        goodUpdatedNotification.shouldNotBe(visible, Duration.ofSeconds(6)); // ожидаем когда пропадет уведомление, так как мешает нажатию дальше на юи

        // проверяем изменения на главной
        homeLink.click();

        peretsProductCard
                .shouldBe(visible)
                .as("Изменения применились в списке товаров")
                .shouldHave(attribute("data-price", "25"));

        pomidorProductCard.shouldNotBe(visible);

        // удаляем перец для повторного запуска
        adminLink.click();
        deletePeretsButton.click();
        Selenide.confirm();
        goodDeletedNotification.shouldBe(visible);
    }


    @AfterEach
            // удаление сырка из теста через апишку из задания 3.1
    void tearDown() {
        try {
            if (createdGoodId != null) {
                given()
                        .spec(basicRQ)
                        .auth()
                        .basic("admin", "secret123")
                        .delete("/goods/{id}", createdGoodId)
                        .then()
                        .statusCode(200);
            }
        } finally {
            Selenide.closeWebDriver();
        }
    }
}
