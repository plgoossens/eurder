package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CustomersMapperTest {

    final String firstName = "firstName";
    final String lastName = "lastName";
    final String email = "email@address.com";
    final String address = "Address 1, 1000 AddressCity";
    final String phoneNumber = "025556677";

    private CustomersMapper customersMapper;

    @BeforeEach
    void setup(){
        customersMapper = new CustomersMapper();
    }

    @Test
    void createCustomerDTOtoDomain() {
        // Given
        CreateCustomerDTO input = getDummyCreateCustomerDTO();
        Customer expected = getDummyCustomer();

        // When
        Customer result = customersMapper.toDomain(input);

        // Then
        assertThat(expected.getFirstName()).isEqualTo(result.getFirstName());
        assertThat(expected.getLastName()).isEqualTo(result.getLastName());
        assertThat(expected.getEmail()).isEqualTo(result.getEmail());
        assertThat(expected.getAddress()).isEqualTo(result.getAddress());
        assertThat(expected.getPhoneNumber()).isEqualTo(result.getPhoneNumber());
    }

    @Test
    void toIdDTO() {
        // Given
        Customer input = getDummyCustomer();

        // When
        IdDTO result = customersMapper.toIdDTO(input);

        // Then
        assertThat(result.getId()).isEqualTo(input.getId().toString());
    }

    private Customer getDummyCustomer(){
        return new Customer(firstName, lastName, email, address, phoneNumber);
    }

    private CreateCustomerDTO getDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(firstName, lastName, email, address, phoneNumber);
    }
}