package com.example.order.repo;

import com.example.order.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Integer> {

    @Query("SELECT c FROM Product c where c.productID=:Id or c.productName =:name ")
    Optional<Product> findByCredentials(Integer Id, String name);

    @Query(value = "SELECT * FROM PRODUCT   where PRODUCTID=:Id or PRODUCT_NAME = :name",nativeQuery = true)
    List<Product> findByProductIDOrName(Integer Id, String name);


}
