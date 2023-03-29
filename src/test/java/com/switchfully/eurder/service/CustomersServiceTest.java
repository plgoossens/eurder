package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.mappers.CustomersMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

public class CustomersServiceTest {

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
        assertThatThrownBy(() -> customersService.createCustomer(createCustomerDTO)).isInstanceOf(IllegalArgumentException.class)
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

    @Test
    void getCustomerByID_whenCustomerExists() {
        // Given
        Customer expected = getDummyCustomer();
        Mockito.when(customersRepository.getById(expected.getId())).thenReturn(expected);

        // When
        Customer result = customersService.getCustomerById(expected.getId());

        // Then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getCustomerByID_whenCustomerDoesntExist_thenThrowCustomerNotFoundException() {
        assertThatThrownBy(() -> customersService.getCustomerById("fakeId")).isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with id fakeId was not found.");
    }
}