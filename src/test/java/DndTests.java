import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import config.TestConfig;
import org.junit.jupiter.api.*;

import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class DndTests {


    SelenideElement sveklaProductCard = $x("//*[contains(@class, 'product-card') and @data-name='" + TestConfig.START_PRODUCT_NAME + "']");

    // Корзина
    SelenideElement addSveklaToCartButton = $x("//*[(@data-action='add-to-cart') and @data-name='" + TestConfig.START_PRODUCT_NAME + "']");
    SelenideElement openCartButton = $x("//*[@id='open-cart-btn']");
    SelenideElement cartCount = $x("//*[@id='cart-count']");
    SelenideElement makeOrderButton = $x("//*[@id='makeOrder']");
    SelenideElement cartItem = $x("//div[@id='cart-items']//div[contains(@class, 'cart-item')]");
    SelenideElement cartItemName = $x("//div[@id='cart-items']//div[contains(@class, 'cart-item')]//b");
    SelenideElement removeFromCartButton = $x("//div[@id='cart-items']//button[@data-action='remove']");
    SelenideElement emptyCartMessage = $x("//*[@id='empty-cart']");
    SelenideElement totalPrice = $x("//*[@id='total-price']");

    @BeforeEach
    void setup() {
        Configuration.timeout = TestConfig.ELEMENT_TIMEOUT;
        open(TestConfig.STAND_URL);
    }

    @Test
    @Tag("main")
    void checkDragAndDropSveklaToCartTest() {
        sveklaProductCard.shouldBe(visible);

        double sveklaPrice = Double.parseDouble(
                Objects.requireNonNull(sveklaProductCard.getAttribute("data-price")));

        sveklaProductCard.dragAndDrop(
                DragAndDropOptions.to(openCartButton).usingSeleniumActions());

        cartCount.shouldHave(text("1"));

        openCartButton.click();
        makeOrderButton.shouldBe(visible);

        cartItemName
                .as(TestConfig.START_PRODUCT_NAME + " должна появиться в корзине после Drag-and-Drop")
                .shouldHave(text(TestConfig.START_PRODUCT_NAME));

        assertThat(Double.parseDouble(totalPrice.getText()))
                .as("Сумма корзины должна равняться цене Свеклы")
                .isEqualTo(sveklaPrice, within(0.001));
    }

    @Test
    @Tag("main")
    void checkRemoveSveklaFromCartTest() {
        addSveklaToCartButton.click();
        cartCount.shouldHave(text("1"));

        openCartButton.click();
        makeOrderButton.shouldBe(visible);

        cartItemName
                .as(TestConfig.START_PRODUCT_NAME + " должна быть добавлена в корзину")
                .shouldHave(text(TestConfig.START_PRODUCT_NAME));

        removeFromCartButton.click();

        cartItem.shouldNotBe(visible);

        cartCount
                .as("Счетчик корзины должен стать нулевым")
                .shouldHave(text("0"));

        totalPrice
                .as("Сумма корзины должна стать нулевой")
                .shouldHave(text("0"));

        emptyCartMessage
                .shouldBe(visible)
                .as("В корзине должно отображаться сообщение о пустоте")
                .shouldHave(text("Пусто"));
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}
