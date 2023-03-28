package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.mappers.CustomersMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

class CustomersServiceTest {

    final String firstName = "firstName";
    final String lastName = "lastName";
    final String email = "email@address.com";
    final String address = "Address 1, 1000 AddressCity";
    final String phoneNumber = "025556677";

    private CustomersService customersService;
    private CustomersMapper customersMapper;
    private CustomersRepository customersRepository;

    @BeforeEach
    void setup(){
        customersMapper = Mockito.mock(CustomersMapper.class);
        customersRepository = Mockito.mock(CustomersRepository.class);
        customersService = new CustomersService(customersMapper, customersRepository);
    }


    @Test
    void createCustomer_whenCreateCustomerDTOPartiallyNull_thenThrowsIllegalArgumentException() {
        CreateCustomerDTO createCustomerDTO = getPartiallyNullDummyCreateCustomerDTO();
        assertThatThrownBy(() -> {
            customersService.createCustomer(createCustomerDTO);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To create a customer, these fields need to be completed : firstName, lastName, email, address and phoneNumber.");
    }

    @Test
    void createCustomer() {
        //Given
        CreateCustomerDTO createCustomerDTO = getDummyCreateCustomerDTO();
        Customer customer = getDummyCustomer();

        Mockito.when(customersMapper.toDomain(createCustomerDTO)).thenReturn(customer);

        //When
        customersService.createCustomer(createCustomerDTO);

        //Then
        Mockito.verify(customersRepository).add(customer);
    }

    private Customer getDummyCustomer(){
        return new Customer(firstName, lastName, email, address, phoneNumber);
    }

    private CreateCustomerDTO getPartiallyNullDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(firstName, null, null, address, phoneNumber);
    }

    private CreateCustomerDTO getDummyCreateCustomerDTO(){
        return new CreateCustomerDTO(firstName, lastName, email, address, phoneNumber);
    }
}