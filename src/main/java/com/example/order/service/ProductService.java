package com.example.order.service;

import com.example.order.dto.ProductDTO;
import com.example.order.entity.Product;
import com.example.order.mapper.ProductMapper;
import com.example.order.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
	ProductRepository repository;

    public ProductDTO getProduct(Integer productId) {
        Optional<Product> result = repository.findById(productId);
        ProductDTO productDTO = null;
        if (result.isPresent()) {
            Product product = result.get();
            productDTO = ProductMapper.INSTANCE.getProductDTO(product);
        }
        return productDTO;
    }

    public List<ProductDTO> getProducts() {
        Iterable<Product> products = repository.findAll();
        return ProductMapper.INSTANCE.getProductDTOList(products);
    }
}
