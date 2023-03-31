package com.switchfully.eurder.api;

import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.dto.ItemDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemsControllerTest {

    @LocalServerPort
    private int port;

    @DirtiesContext
    @Test
    void addAnItem_whenGivingFullCreateItemDTO_thenReturnsIdDTO() {
        // Given
        CreateItemDTO input = new CreateItemDTO("Item name", "Description", 10.0, 10);

        // When
        IdDTO result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(IdDTO.class);

        assertThat(result.getId())
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void addAnItem_whenGivingPartiallyNullCreateItemDTO_thenReturnsBadRequestHttpStatus() {
        // Given
        CreateItemDTO input = new CreateItemDTO("Item name", null, null, 10);

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addAnItem_whenGivingNegativeValues_thenReturnsBadRequestHttpStatus() {
        // Given
        CreateItemDTO input = new CreateItemDTO("Item name", "description", 1.0, -10);

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DirtiesContext
    @Test
    void getItemOverview() {
        // Given
        CreateItemDTO stockLowItem = new CreateItemDTO("Item name", "description", 1.0, 2);
        CreateItemDTO stockMediumItem = new CreateItemDTO("Item name", "description", 1.0, 7);
        CreateItemDTO stockHighItem = new CreateItemDTO("Item name", "description", 1.0, 15);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(stockMediumItem)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(stockHighItem)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(stockLowItem)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items");

        // When

        ItemDTO[] result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ItemDTO[].class);

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .matches(itemDTOS -> itemDTOS[0].getAmount() == 2)
                .matches(itemDTOS -> itemDTOS[1].getAmount() == 7)
                .matches(itemDTOS -> itemDTOS[2].getAmount() == 15);
    }


    @DirtiesContext
    @Test
    void getItemOverview_withFilter() {
        // Given
        CreateItemDTO stockLowItem = new CreateItemDTO("Item name", "description", 1.0, 2);
        CreateItemDTO stockMediumItem = new CreateItemDTO("Item name", "description", 1.0, 7);
        CreateItemDTO stockHighItem = new CreateItemDTO("Item name", "description", 1.0, 15);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(stockMediumItem)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(stockHighItem)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(stockLowItem)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items");

        // When

        ItemDTO[] result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .baseUri("http://localhost")
                .port(port)
                .param("urgencyLevel", "STOCK_LOW")
                .when()
                .get("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ItemDTO[].class);

        // Then

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .matches(itemDTOS -> itemDTOS[0].getAmount() == 2);
    }

    @DirtiesContext
    @Test
    void updateItem() {
        // Given
        CreateItemDTO item = new CreateItemDTO("Item name", "description", 1.0, 2);
        CreateItemDTO updateItem = new CreateItemDTO("Item name updated", "description updated", 10.0, 3);

        IdDTO idDTO = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(item)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .extract()
                .as(IdDTO.class);

        // When

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .baseUri("http://localhost")
                .body(updateItem)
                .port(port)
                .when()
                .put("/items/" + idDTO.getId())
                .then()
        // Then
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        ItemDTO[] result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/items")
                .then()
                .extract()
                .as(ItemDTO[].class);

        assertThat(result[0])
                .matches(resultItem -> resultItem.getName().equals(updateItem.getName()))
                .matches(resultItem -> resultItem.getDescription().equals(updateItem.getDescription()))
                .matches(resultItem -> resultItem.getAmount() == updateItem.getAmount())
                .matches(resultItem -> resultItem.getPrice() == updateItem.getPrice());
    }

    @DirtiesContext
    @Test
    void updateItem_withPartiallyNull() {
        // Given
        CreateItemDTO item = new CreateItemDTO("Item name", "description", 1.0, 2);
        CreateItemDTO updateItem = new CreateItemDTO(null, "description updated", 10.0, null);

        IdDTO idDTO = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(item)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .extract()
                .as(IdDTO.class);

        // When

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .baseUri("http://localhost")
                .body(updateItem)
                .port(port)
                .when()
                .put("/items/" + idDTO.getId())
                .then()
                // Then
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        ItemDTO[] result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/items")
                .then()
                .extract()
                .as(ItemDTO[].class);

        assertThat(result[0])
                .matches(resultItem -> resultItem.getName().equals(item.getName()))
                .matches(resultItem -> resultItem.getDescription().equals(updateItem.getDescription()))
                .matches(resultItem -> resultItem.getPrice() == updateItem.getPrice())
                .matches(resultItem -> resultItem.getAmount() == item.getAmount());
    }
}