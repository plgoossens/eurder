package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.domain.repositories.CustomersRepository;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import com.switchfully.eurder.service.mappers.CustomersMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {

    private final CustomersMapper customersMapper;
    private final CustomersRepository customersRepository;

    public CustomersService(CustomersMapper customersMapper, CustomersRepository customersRepository) {
        this.customersMapper = customersMapper;
        this.customersRepository = customersRepository;
    }

    public IdDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
        validateCreateCustomerDTO(createCustomerDTO);
        Customer customer = customersMapper.toDomain(createCustomerDTO);
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
}
