package testsApi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("api")
public class GoodsListTests {

    private final RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .log(LogDetail.ALL)
            .addQueryParam("page", 0)
            .addQueryParam("size", "10000")
            .build();

    // 1.1 — given/when/then
    @Test
    @Tag("api")
        public void listIsEmptyTest() {
            given()
                    .baseUri("http://localhost:8080")
                    .log().all()
                    .queryParam("page", 0)
                    .queryParam("size", "10000")

                    .when()
                    .get("/goods/list")

                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("goods", empty());
        }

// 1.2 — basicRQ
    @Test
    @Tag("api")
    public void listIsEmptyTestWithRQ() {
        given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    // 1.3 — POST → GET, проверка через встроенные проверки REST Assured
   @Test
   @Tag("api")
    public void createGoodInListTest() {
       String jsonBody = """
                {
                    "name": "дыня",
                    "price": 1.5
                }
                """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem("дыня"))
                .body("goods.price", hasItem(1.5f));
    }

    // 1.4 — POST → GET, проверка через Assert
   @Test
   @Tag("api")
    public void createGoodInListWithAssertTest() {
       String jsonBody = """
                {
                    "name": "яблоко",
                    "price": 1.6
                }
                """;

       given()
               .spec(basicRQ)
               .auth()
               .basic("admin", "secret123")
               .contentType(ContentType.JSON)
               .body(jsonBody)
               .when()
               .post("/goods/add")
               .then()
               .log().all()
               .statusCode(200);

       Response response = given()
               .spec(basicRQ)
               .contentType(ContentType.JSON)
               .when()
               .get("/goods/list")
               .then()
               .log().all()
               .statusCode(200)
               .extract().response();

       response.then()
               .body("goods.name", hasItem("яблоко"))
               .body("goods.price", hasItem(1.6f));

       List<String> names = response.jsonPath().getList("goods.name");
       List<Float> prices = response.jsonPath().getList("goods.price", Float.class);

       assertTrue(names.contains("яблоко"), "Содержит товар с именем 'яблоко'");

       assertEquals(1.6f, prices.get(prices.indexOf(1.6f)), 0.0001f,
               "Цена товара 'яблоко' = 1.6");
   }

    // POST /goods/add — 200
    @Test
    @Tag("api")
    public void addSuccessTest() {
        String jsonBody = """
                {
                    "name": "яблоко200",
                    "price": 1.6
                }
                """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);
    }

    // POST /goods/add — 401
    @Test
    @Tag("api")
    public void addUnauthorizedTest() {
        String jsonBody = """
                {
                    "name": "яблоко401",
                    "price": 1.6
                }
                """;

        given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(401);
    }

    // POST /goods/add — 401
    @Test
    @Tag("api")
    public void addBadRequestTest() {
        String jsonBody = """
                {
                    "namee": "яблоко400",
                    "pricee": 1.6
                }
                """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(400);
    }

    // GET /goods/{id} — 200
    @Test
    @Tag("api")
    public void getGoodIdSuccessTest() {
        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .get("/goods/{id}", 1)
                .then()
                .statusCode(200);
    }

    // GET /goods/{id} — 401
    @Test
    @Tag("api")
    public void getGoodIdUnauthorizedTest() {
        given()
                .spec(basicRQ)
                .get("/goods/{id}", 38)
                .then()
                .statusCode(401);
    }

    // GET /goods/{id} — 400
    @Test
    @Tag("api")
    public void getGoodIdBadRequestTest() {
        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .get("/goods/{id}", "bad")
                .then()
                .statusCode(400);
    }

    // GET /goods/{id} — 404
    @Test
    @Tag("api")
    public void getGoodIdNotFoundTest() {
        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .get("/goods/{id}", "99")
                .then()
                .statusCode(404);
    }

    // DELETE /goods/{id} — 200
    @Test
    @Tag("api")
    public void deleteGoodSuccessTest() {
        String jsonBody = """
                {
                    "name": "огурец200",
                    "price": 1.5
                }
                """;

        Integer id = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .delete("/goods/{id}", id)
                .then()
                .statusCode(200);
    }

    // DELETE /goods/{id} — 401
    @Test
    @Tag("api")
    public void deleteGoodUnauthorizedTest() {
        String jsonBody = """
                {
                    "name": "огурец401",
                    "price": 1.5
                }
                """;

        Integer id = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");

        given()
                .spec(basicRQ)
                .contentType(ContentType.JSON)
                .delete("/goods/{id}", id)
                .then()
                .statusCode(401);
    }

    // DELETE /goods/{id} — 400
    @Test
    @Tag("api")
    public void deleteGoodBadRequestTest() {
        String jsonBody = """
                {
                    "name": "огурец400",
                    "price": 1.5
                }
                """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .delete("/goods/{id}", "id")
                .then()
                .statusCode(400);
    }

    // DELETE /goods/{id} — 404
    @Test
    @Tag("api")
    public void deleteGoodNotFoundTest() {
        String jsonBody = """
                {
                    "name": "огурец404",
                    "price": 1.5
                }
                """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .delete("/goods/{id}", "99")
                .then()
                .statusCode(404);
    }

    // PATCH /goods/{id} — 200
    @Test
    @Tag("api")
    public void patchGoodSuccessTest() {
        String jsonBody = """
                {
                    "name": "бананы200",
                    "price": 1.5
                }
                """;

        Integer id = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");;

        String patchBody = """
        {
            "name": "бананы200",
            "price": 1.6
        }
        """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(patchBody)
                .when()
                .patch("/goods/{id}", id)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo("бананы200"))
                .body("price", equalTo(1.6f));
    }

    // PATCH /goods/{id} — 400
    @Test
    @Tag("api")
    public void patchGoodBadRequestTest() {
        String jsonBody = """
                {
                    "name": "бананы4000",
                    "price": 1.5
                }
                """;

        Integer id = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");;

        String patchBody = """
        {
            "name": "бананы4000",
            "price": 1.6
        }
        """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(patchBody)
                .when()
                .patch("/goods/{id}", "id")
                .then()
                .log().all()
                .statusCode(400);
    }

    // PATCH /goods/{id} — 404
    @Test
    @Tag("api")
    public void patchGoodNotFoundTest() {
        String jsonBody = """
                {
                    "name": "бананы4001",
                    "price": 1.5
                }
                """;

        Integer id = given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("data.id");;

        String patchBody = """
        {
            "name": "бананы4001",
            "price": 1.6
        }
        """;

        given()
                .spec(basicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(patchBody)
                .when()
                .patch("/goods/{id}", "99")
                .then()
                .log().all()
                .statusCode(404);
    }

    // GET /goods/list — 200
    @Test
    @Tag("api")
    public void getListSuccessTest() {
        given()
                .spec(basicRQ)
                .get("/goods/list")
                .then()
                .statusCode(200);
    }
}
