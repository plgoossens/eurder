package com.switchfully.eurder;

import com.switchfully.eurder.domain.models.*;
import com.switchfully.eurder.service.dto.*;
import com.switchfully.eurder.service.wrappers.CreateCustomerWrapper;

import java.time.LocalDate;
import java.util.List;

public class TestsUtils {
    private static final String ITEM_ID = "item-id-123456";
    private static final String ITEM_NAME = "Item";
    private static final String ITEM_DESCRIPTION = "Description";
    private static final Double ITEM_PRICE = 9.99;
    private static final Integer ITEM_AMOUNT = 5;

    private static final String CUSTOMER_ID = "customer-id-123456";
    private static final String CUSTOMER_FIRSTNAME = "firstName";
    private static final String CUSTOMER_LASTNAME = "lastName";
    private static final String CUSTOMER_EMAIL = "email@address.com";
    private static final String CUSTOMER_ADDRESS = "Address 1, 1000 AddressCity";
    private static final String CUSTOMER_PHONENUMBER = "025556677";

    private static final String ORDER_ID = "order-id-123456";
    private static final double ORDER_PRICE = ITEM_PRICE*ITEM_AMOUNT;

    private static final String CUSTOMER_USERNAME = "customer";
    private static final String CUSTOMER_PASSWORD = "password";

    public static Item getDummyItem(){
        return new Item(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT);
    }

    public static CreateItemDTO getPartiallyNullDummyCreateItemDTO(){
        return new CreateItemDTO(ITEM_NAME, null, ITEM_PRICE, null);
    }

    public static CreateItemDTO getDummyCreateItemDTOWithNegativeValues(){
        return new CreateItemDTO(ITEM_NAME, ITEM_DESCRIPTION, -ITEM_PRICE, ITEM_AMOUNT);
    }

    public static CreateItemDTO getDummyCreateItemDTO(){
        return new CreateItemDTO(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT);
    }

    public static Customer getDummyCustomer(){
        return new Customer(CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, CUSTOMER_ADDRESS, CUSTOMER_PHONENUMBER);
    }

    public static CreateCustomerDTO getPartiallyNullDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(CUSTOMER_FIRSTNAME, null, null, CUSTOMER_ADDRESS, CUSTOMER_PHONENUMBER);
    }

    public static CreateCustomerDTO getDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, CUSTOMER_ADDRESS, CUSTOMER_PHONENUMBER);
    }

    public static CreateItemGroupDTO getDummyCreateItemGroupDTO(){
        return new CreateItemGroupDTO(ITEM_ID, ITEM_AMOUNT);
    }

    public static ItemGroup getDummyItemGroup(){
        return new ItemGroup(getDummyItem(), ITEM_AMOUNT);
    }

    public static ItemGroup getDummyItemGroupNotInStock(){
        return new ItemGroup(getDummyItem(), ITEM_AMOUNT*2);
    }

    public static ItemGroupDTO getDummyItemGroupInStockDTO(){
        return new ItemGroupDTO(ITEM_ID, ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT, LocalDate.now().plusDays(1));
    }

    public static ItemGroupDTO getDummyItemGroupNotInStockDTO(){
        return new ItemGroupDTO(ITEM_ID, ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT*2, LocalDate.now().plusDays(7));
    }

    public static CreateOrderDTO getDummyCreateOrderDTO(){
        return new CreateOrderDTO(List.of(getDummyCreateItemGroupDTO()));
    }

    public static CreateOrderDTO getPartiallyNullDummyCreateOrderDTO(){
        return new CreateOrderDTO(null);
    }

    public static Order getDummyOrder(){
        return new Order(CUSTOMER_ID, List.of(getDummyItemGroup()));
    }

    public static OrderDTO getDummyOrderDTO(){
        return new OrderDTO(ORDER_ID, ORDER_PRICE, List.of(getDummyItemGroupInStockDTO()));
    }

    public static CreateCredentialsDTO getDummyCreateCredentialsDTO(){
        return new CreateCredentialsDTO(CUSTOMER_USERNAME, CUSTOMER_PASSWORD);
    }

    public static CreateCustomerWrapper getDummyCreateCustomerWrapper(){
        return new CreateCustomerWrapper(getDummyCreateCustomerDTO(), getDummyCreateCredentialsDTO());
    }

    public static Credentials getDummyCredentials(){
        return new Credentials(CUSTOMER_USERNAME, CUSTOMER_PASSWORD, Role.CUSTOMER, CUSTOMER_ID);
    }

}
