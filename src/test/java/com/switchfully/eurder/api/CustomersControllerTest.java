package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomersControllerTest {

    @LocalServerPort
    private int port;

    @DirtiesContext
    @Test
    void createCustomer_whenGivingFullCreateCustomerDTO_thenReturnsIdDTO() {
        // Given
        CreateCustomerDTO input = new CreateCustomerDTO("first name", "last name", "email@address.com", "address", "025556677");

        // When
        IdDTO result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/customers")
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
    void createCustomer_whenGivingPartiallyEmptyCreateCustomerDTO_thenReturnsBadRequestHttpStatus() {
        // Given
        CreateCustomerDTO input = new CreateCustomerDTO("first name", null, null, "address", "025556677");

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getCustomersList_whenCustomersListIsEmpty() {
        Customer[] result = RestAssured
                .given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Customer[].class);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @DirtiesContext
    @Test
    void getCustomersList_whenCustomersListIsNotEmpty() {
        addCustomer();
        addCustomer();

        Customer[] result = RestAssured
                .given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Customer[].class);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
        assertThat(result.length).isEqualTo(2);
    }

    private void addCustomer() {
        CreateCustomerDTO input = new CreateCustomerDTO("first name", "last name", "email@address.com", "address", "025556677");

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(input)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/customers");
    }
}