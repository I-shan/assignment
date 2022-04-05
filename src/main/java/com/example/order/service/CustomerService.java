package com.example.order.service;

import com.example.order.dto.CustomerDTO;
import com.example.order.entity.Customer;
import com.example.order.mapper.CustomerMapper;
import com.example.order.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional

public class CustomerService {

    @Autowired
	CustomerRepository customerRepository;

    public String addCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
            return " customer added";
        } catch (Exception ex) {
            return "error happened" + ex;
        }
    }

    public CustomerDTO getCustomer(Integer id) {
        Optional<Customer> optional = customerRepository.findByCustomerId(id);
        CustomerDTO customerDTO = null;
        if (optional.isPresent()) {
            Customer customer = optional.get();
            customerDTO = CustomerMapper.INSTANCE.getCustomerDTO(customer);
        }
        return customerDTO;
    }

    public List<CustomerDTO> getCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> dtos = CustomerMapper.INSTANCE.list(customers);
        return dtos;
    }

    public String updateCustomer(Integer id, Customer customer) {
        Optional<Customer> result = customerRepository.findById(id);
        customerRepository.updateCustomer(id, customer.getFirstName(), customer.getLastName(), customer.getCategory());
        return "customer updated";
    }
}
