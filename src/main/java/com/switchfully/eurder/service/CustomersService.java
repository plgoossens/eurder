package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.exceptions.exceptions.CustomerNotFoundException;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.mappers.CustomersMapper;
import com.switchfully.eurder.service.wrappers.CreateCustomerWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomersService {

    private final CustomersMapper customersMapper;
    private final CustomersRepository customersRepository;
    private final CredentialsService credentialsService;

    public CustomersService(CustomersMapper customersMapper, CustomersRepository customersRepository, CredentialsService credentialsService) {
        this.customersMapper = customersMapper;
        this.customersRepository = customersRepository;
        this.credentialsService = credentialsService;
    }

    public IdDTO createCustomer(CreateCustomerWrapper createCustomerWrapper) {
        validateCreateCustomerDTO(createCustomerWrapper.getCustomer());
        credentialsService.validateUsernameDoesntExist(createCustomerWrapper.getCredentials().getUsername());
        Customer customer = customersMapper.toDomain(createCustomerWrapper.getCustomer());
        credentialsService.addCustomer(createCustomerWrapper.getCredentials().getUsername(), createCustomerWrapper.getCredentials().getPassword(), customer.getId());
        customersRepository.add(customer);
        return customersMapper.toIdDTO(customer);
    }

    private void validateCreateCustomerDTO(CreateCustomerDTO createCustomerDTO){
        if(createCustomerDTO.getFirstName() == null ||
                createCustomerDTO.getLastName() == null ||
                createCustomerDTO.getEmail() == null ||
                createCustomerDTO.getAddress() == null ||
                createCustomerDTO.getPhoneNumber() == null){
            throw new IllegalArgumentException("To create a customer, these fields need to be completed : firstName, lastName, email, address and phoneNumber.");
        }
    }

    public Collection<Customer> getCustomersList() {
        return customersRepository.getCustomersList();
    }

    public Customer getCustomerById(String id) {
        return customersRepository.getById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " was not found."));
    }
}
