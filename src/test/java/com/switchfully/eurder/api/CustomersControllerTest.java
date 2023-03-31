package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.CustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.wrappers.CreateCustomerWrapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.switchfully.eurder.TestsUtils.*;
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
        CreateCustomerWrapper input = getDummyCreateCustomerWrapper();

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

    @DirtiesContext
    @Test
    void createCustomer_whenUsernameAlreadyExists_thenReturnsForbiddenHttpStatus() {
        // Given
        CreateCustomerWrapper input = getDummyCreateCustomerWrapper();

        // When
        RestAssured
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
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
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
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void createCustomer_whenGivingPartiallyEmptyCreateCustomerDTO_thenReturnsBadRequestHttpStatus() {
        // Given
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO("first name", null, null, "address", "025556677");
        CreateCustomerWrapper input = new CreateCustomerWrapper(createCustomerDTO, getDummyCreateCredentialsDTO());

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
        CustomerDTO[] result = RestAssured
                .given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO[].class);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @DirtiesContext
    @Test
    void getCustomersList_whenCustomersListIsNotEmpty() {
        addCustomer();

        CustomerDTO[] result = RestAssured
                .given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO[].class);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
        assertThat(result.length).isEqualTo(1);
    }

    @DirtiesContext
    @Test
    void getCustomerByID_whenCustomerExists() {
        String id = addCustomer();
        Customer expected = getDummyCustomer();

        CustomerDTO result = RestAssured
                .given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/customers/"+id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerDTO.class);

        assertThat(result)
                .isNotNull()
                .matches(customer -> customer.getId().equals(id))
                .matches(customer -> customer.getFirstName().equals(expected.getFirstName()))
                .matches(customer -> customer.getLastName().equals(expected.getLastName()))
                .matches(customer -> customer.getEmail().equals(expected.getEmail()))
                .matches(customer -> customer.getAddress().equals(expected.getAddress()))
                .matches(customer -> customer.getPhoneNumber().equals(expected.getPhoneNumber()));
    }

    @Test
    void getCustomerByID_whenCustomerDoesntExists() {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/customers/fakeId")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private String addCustomer() {
        // When
        IdDTO idDTO = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(getDummyCreateCustomerWrapper())
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/customers")
                .then()
                .extract()
                .as(IdDTO.class);

        return idDTO.getId();
    }
}