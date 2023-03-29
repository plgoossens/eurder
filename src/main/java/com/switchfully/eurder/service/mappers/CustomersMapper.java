package com.switchfully.eurder.service.mappers;

import com.switchfully.eurder.domain.models.Customer;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.IdDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomersMapper {
    public Customer toDomain(CreateCustomerDTO createCustomerDTO) {
        return new Customer(createCustomerDTO.getFirstName(), createCustomerDTO.getLastName(), createCustomerDTO.getEmail(), createCustomerDTO.getAddress(), createCustomerDTO.getPhoneNumber());
    }

    public IdDTO toIdDTO(Customer customer) {
        return new IdDTO(customer.getId());
    }
}
