package com.switchfully.eurder.api;

import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.CreateItemDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemsControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void addAnItem_whenGivingFullCreateItemDTO_thenReturnsIdDTO() {
        // Given
        CreateItemDTO input = new CreateItemDTO("Item name", "Description", 10.0, 10);

        // When
        IdDTO result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
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
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}