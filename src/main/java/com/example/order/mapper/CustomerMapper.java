package com.example.order.mapper;

import com.example.order.dto.CustomerDTO;
import com.example.order.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO getCustomerDTO(Customer customer);

    List<CustomerDTO> list(Iterable<Customer> customers);

}
