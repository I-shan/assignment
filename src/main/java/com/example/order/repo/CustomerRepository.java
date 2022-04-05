package com.example.order.repo;

import com.example.order.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    Optional<Customer> findByCustomerId(Integer Id);

    @Query(value = "SELECT * FROM CUSTOMER   where CUSTOMER_ID=:Id or FIRST_NAME=:firsName or LAST_NAME=:lastName",nativeQuery = true)
    List<Customer> findByCustomerIdOrFirstNameOrLastName(Integer Id, String firsName, String lastName);

    @Modifying
    @Query(value = "UPDATE CUSTOMER SET FIRST_NAME = :firstName, LAST_NAME = :lastName, TYPE = :type WHERE CUSTOMER_ID = :customerId", nativeQuery = true)
    void updateCustomer(Integer customerId, String firstName, String lastName, String type);
}
