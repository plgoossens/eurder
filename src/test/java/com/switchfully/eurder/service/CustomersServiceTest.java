package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.CustomerDTO;
import com.switchfully.eurder.service.mappers.CustomersMapper;
import com.switchfully.eurder.service.wrappers.CreateCustomerWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.switchfully.eurder.TestsUtils.*;
import static org.assertj.core.api.Assertions.*;

public class CustomersServiceTest {

    private CustomersService customersService;
    private CustomersMapper customersMapper;
    private CustomersRepository customersRepository;
    private CredentialsService credentialsService;

    @BeforeEach
    void setup(){
        customersMapper = Mockito.mock(CustomersMapper.class);
        customersRepository = Mockito.mock(CustomersRepository.class);
        credentialsService = Mockito.mock(CredentialsService.class);
        customersService = new CustomersService(customersMapper, customersRepository, credentialsService);
    }


    @Test
    void createCustomer_whenCreateCustomerDTOPartiallyNull_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> customersService.createCustomer(new CreateCustomerWrapper(getPartiallyNullDummyCreateCustomerDTO(), getDummyCreateCredentialsDTO()))).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("To create a customer, these fields need to be completed : firstName, lastName, email, address and phoneNumber.");
    }

    @Test
    void createCustomer() {
        //Given
        CreateCustomerDTO createCustomerDTO = getDummyCreateCustomerDTO();
        Customer customer = getDummyCustomer();
        CreateCustomerWrapper createCustomerWrapper = new CreateCustomerWrapper(createCustomerDTO, getDummyCreateCredentialsDTO());

        Mockito.when(customersMapper.toDomain(createCustomerDTO)).thenReturn(customer);

        //When
        customersService.createCustomer(createCustomerWrapper);

        //Then
        Mockito.verify(customersRepository).add(customer);
    }

    @Test
    void getCustomerByID_whenCustomerExists() {
        // Given
        Customer expectedCustomer = getDummyCustomer();
        CustomerDTO expected = getDummyCustomerDTO();
        Mockito.when(customersRepository.getById(expectedCustomer.getId())).thenReturn(Optional.of(expectedCustomer));
        Mockito.when(customersMapper.toDTO(expectedCustomer)).thenReturn(expected);

        // When
        CustomerDTO result = customersService.getCustomerById(expectedCustomer.getId().toString());

        // Then
        assertThat(result)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void getCustomerByID_whenCustomerDoesntExist_thenThrowCustomerNotFoundException() {
        assertThatThrownBy(() -> customersService.getCustomerById("fakeId")).isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with id fakeId was not found.");
    }
}