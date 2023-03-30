package com.switchfully.eurder.api;

import com.switchfully.eurder.service.dto.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrdersControllerTest {

    @LocalServerPort
    private int port;

    @DirtiesContext
    @Test
    void createOrder_whenGivingFullCreateOrderDTO_thenReturnsOrderDTO() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item2 = addDummyItem();
        IdDTO item3 = addDummyItem();

        addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO(item2.getId(), 1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When
        OrderDTO result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(OrderDTO.class);

        //Then
        assertThat(result)
                .isNotNull()
                .matches(orderDTO -> orderDTO.getTotalPrice() == getDummyCreateItemDTO().getPrice()*3)
                .matches(orderDTO -> orderDTO.getItems().size() == 3)
                .matches(orderDTO -> orderDTO.getItems().get(0).getItemId().equals(item1.getId()))
                .matches(orderDTO -> orderDTO.getItems().get(1).getItemId().equals(item2.getId()))
                .matches(orderDTO -> orderDTO.getItems().get(2).getItemId().equals(item3.getId()));
    }

    @DirtiesContext
    @Test
    void createOrder_whenGivingNegativeAmounts_thenReturnsBadRequestHttpStatus() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item2 = addDummyItem();
        IdDTO item3 = addDummyItem();

        addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO(item2.getId(), -1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DirtiesContext
    @Test
    void createOrder_whenGivingEmptyList_thenReturnsBadRequestHttpStatus() {
        // Given
        addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of());

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DirtiesContext
    @Test
    void createOrder_whenGivingNullList_thenReturnsBadRequestHttpStatus() {
        // Given
        addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(null);

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DirtiesContext
    @Test
    void createOrder_whenCustomerDoesntExist_thenReturnsUnauthorizedHttpStatus() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item2 = addDummyItem();
        IdDTO item3 = addDummyItem();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO(item2.getId(), 1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DirtiesContext
    @Test
    void createOrder_whenItemDoesntExist_thenReturnsBadRequestHttpStatus() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item3 = addDummyItem();

        addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO("item2Id", 1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DirtiesContext
    @Test
    void getOrdersReport() {
        //Given
        IdDTO item = addDummyItem();

        addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(List.of(new CreateItemGroupDTO(item.getId(), 5)));

        OrderReportDTO expected = getDummyOrderReportDTO();

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders");

        //When

        OrderReportDTO orderReportDTO = RestAssured
                .given()
                .accept(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OrderReportDTO.class);

        //Then

        assertThat(orderReportDTO)
                .isNotNull()
                .matches(orderReport -> orderReport.getTotalPrice() == expected.getTotalPrice())
                .matches(orderReport -> orderReport.getOrders().size() == expected.getOrders().size());
    }

    @DirtiesContext
    @Test
    void getOrdersReport_whenCustomerHasNoOrder() {
        //Given
        addDummyCustomer();

        //When

        OrderReportDTO orderReportDTO = RestAssured
                .given()
                .accept(ContentType.JSON)
                .auth().preemptive().basic("customer", "password")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .get("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OrderReportDTO.class);

        //Then

        assertThat(orderReportDTO)
                .isNotNull()
                .matches(orderReport -> orderReport.getTotalPrice() == 0.0)
                .matches(orderReport -> orderReport.getOrders().size() == 0);
    }

    private IdDTO addDummyItem(){
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin", "admin")
                .body(getDummyCreateItemDTO())
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .extract()
                .as(IdDTO.class);
    }

    private void addDummyCustomer(){
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(getDummyCreateCustomerWrapper())
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/customers");
    }
}