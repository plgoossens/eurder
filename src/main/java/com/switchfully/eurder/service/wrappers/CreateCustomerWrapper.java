package com.switchfully.eurder.service.wrappers;

import com.switchfully.eurder.service.dto.CreateCredentialsDTO;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;

public class CreateCustomerWrapper {

    private final CreateCustomerDTO customer;
    private final CreateCredentialsDTO credentials;

    public CreateCustomerWrapper(CreateCustomerDTO customer, CreateCredentialsDTO credentials) {
        this.customer = customer;
        this.credentials = credentials;
    }

    public CreateCustomerDTO getCustomer() {
        return customer;
    }

    public CreateCredentialsDTO getCredentials() {
        return credentials;
    }
}
