package com.switchfully.eurder.api;

import com.switchfully.eurder.service.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.dto.CreateOrderDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.dto.OrderDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrdersControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void createOrder_whenGivingFullCreateOrderDTO_thenReturnsOrderDTO() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item2 = addDummyItem();
        IdDTO item3 = addDummyItem();

        IdDTO customer = addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(customer.getId(), List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO(item2.getId(), 1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When

        OrderDTO result = RestAssured
                .given()
                .contentType(ContentType.JSON)
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

    @Test
    void createOrder_whenGivingEmptyList_thenReturnsBadRequestHttpStatus() {
        // Given
        IdDTO customer = addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(customer.getId(), List.of());

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createOrder_whenGivingNullList_thenReturnsBadRequestHttpStatus() {
        // Given
        IdDTO customer = addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(customer.getId(), null);

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createOrder_whenCustomerDoesntExist_thenReturnsBadRequestHttpStatus() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item2 = addDummyItem();
        IdDTO item3 = addDummyItem();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO("customerid", List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO(item2.getId(), 1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createOrder_whenItemDoesntExist_thenReturnsBadRequestHttpStatus() {
        // Given
        IdDTO item1 = addDummyItem();
        IdDTO item3 = addDummyItem();

        IdDTO customer = addDummyCustomer();

        CreateOrderDTO createOrderDTO = new CreateOrderDTO(customer.getId(), List.of(new CreateItemGroupDTO(item1.getId(), 1), new CreateItemGroupDTO("item2Id", 1), new CreateItemGroupDTO(item3.getId(), 1)));

        // When
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createOrderDTO)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private IdDTO addDummyItem(){
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(getDummyCreateItemDTO())
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/items")
                .then()
                .extract()
                .as(IdDTO.class);
    }

    private IdDTO addDummyCustomer(){
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(getDummyCreateCustomerDTO())
                .baseUri("http://localhost")
                .port(port)
                .when()
                .post("/customers")
                .then()
                .extract()
                .as(IdDTO.class);
    }
}