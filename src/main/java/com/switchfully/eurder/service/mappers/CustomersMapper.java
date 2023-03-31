package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.CustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomersMapper {
    public Customer toDomain(CreateCustomerDTO createCustomerDTO) {
        return new Customer(createCustomerDTO.getFirstName(), createCustomerDTO.getLastName(), createCustomerDTO.getEmail(), createCustomerDTO.getAddress(), createCustomerDTO.getPhoneNumber());
    }

    public CustomerDTO toDTO(Customer customer){
        return new CustomerDTO(customer.getId().toString(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAddress(), customer.getPhoneNumber());
    }
    public Collection<CustomerDTO> toDTO(Collection<Customer> customers){
        return customers.stream()
                .map(this::toDTO)
                .toList();
    }

    public IdDTO toIdDTO(Customer customer) {
        return new IdDTO(customer.getId().toString());
    }
}
