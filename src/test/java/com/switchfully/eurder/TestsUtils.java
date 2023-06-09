package com.switchfully.eurder;

import com.switchfully.eurder.domain.models.*;
import com.switchfully.eurder.service.dto.*;
import com.switchfully.eurder.service.wrappers.CreateCustomerWrapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TestsUtils {
    public static final UUID ITEM_ID = UUID.randomUUID();
    private static final String ITEM_NAME = "Item";
    private static final String ITEM_DESCRIPTION = "Description";
    private static final Double ITEM_PRICE = 9.99;
    public static final Integer ITEM_AMOUNT = 5;
    private static final Integer ITEM_AMOUNT_STOCK_LOW = 1;
    private static final Integer ITEM_AMOUNT_STOCK_HIGH = 15;

    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    private static final String CUSTOMER_FIRSTNAME = "firstName";
    private static final String CUSTOMER_LASTNAME = "lastName";
    private static final String CUSTOMER_EMAIL = "email@address.com";
    private static final String CUSTOMER_ADDRESS = "Address 1, 1000 AddressCity";
    private static final String CUSTOMER_PHONENUMBER = "025556677";

    private static final UUID ORDER_ID = UUID.randomUUID();
    private static final double ORDER_PRICE = ITEM_PRICE*ITEM_AMOUNT;

    private static final String CUSTOMER_USERNAME = "customer";
    private static final String CUSTOMER_PASSWORD = "password";

    public static Item getDummyItem(){
        return new Item(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT);
    }
    public static Item getDummyItemStockLow(){
        return new Item(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT_STOCK_LOW);
    }
    public static Item getDummyItemStockHigh(){
        return new Item(ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT_STOCK_HIGH);
    }

    public static ItemDTO getDummyItemDTO(){
        return new ItemDTO(ITEM_ID.toString(), ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT, UrgencyLevel.STOCK_MEDIUM);
    }

    public static ItemDTO getDummyItemDTOStockLow(){
        return new ItemDTO(ITEM_ID.toString(), ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT, UrgencyLevel.STOCK_LOW);
    }

    public static ItemDTO getDummyItemDTOStockHigh(){
        return new ItemDTO(ITEM_ID.toString(), ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT, UrgencyLevel.STOCK_HIGH);
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

    public static CustomerDTO getDummyCustomerDTO(){
        return new CustomerDTO(CUSTOMER_ID.toString(), CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, CUSTOMER_ADDRESS, CUSTOMER_PHONENUMBER);
    }

    public static CreateCustomerDTO getPartiallyNullDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(CUSTOMER_FIRSTNAME, null, null, CUSTOMER_ADDRESS, CUSTOMER_PHONENUMBER);
    }

    public static CreateCustomerDTO getDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, CUSTOMER_ADDRESS, CUSTOMER_PHONENUMBER);
    }

    public static CreateItemGroupDTO getDummyCreateItemGroupDTO(){
        return new CreateItemGroupDTO(ITEM_ID.toString(), ITEM_AMOUNT);
    }

    public static ItemGroup getDummyItemGroup(){
        return new ItemGroup(getDummyItem(), ITEM_AMOUNT);
    }

    public static ItemGroup getDummyItemGroupNotInStock(){
        return new ItemGroup(getDummyItem(), ITEM_AMOUNT*2);
    }

    public static ItemGroupDTO getDummyItemGroupInStockDTO(){
        return new ItemGroupDTO(ITEM_ID.toString(), ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT, LocalDate.now().plusDays(1));
    }

    public static ItemGroupDTO getDummyItemGroupNotInStockDTO(){
        return new ItemGroupDTO(ITEM_ID.toString(), ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT*2, LocalDate.now().plusDays(7));
    }

    public static ItemGroupOrderDTO getDummyItemGroupOrderDTO(){
        return new ItemGroupOrderDTO(ITEM_ID.toString(), ITEM_NAME, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_AMOUNT, ITEM_PRICE*ITEM_AMOUNT, LocalDate.now().plusDays(1), CUSTOMER_ADDRESS);
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
        return new OrderDTO(ORDER_ID.toString(), ORDER_PRICE, List.of(getDummyItemGroupInStockDTO()));
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

    public static OrderReportDTO getDummyOrderReportDTO(){
        return new OrderReportDTO(List.of(getDummyOrderDTO()), ORDER_PRICE);
    }

}
