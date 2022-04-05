package com.example.order.mapper;


import com.example.order.dto.ProductDTO;
import com.example.order.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO getProductDTO(Product product);

    List<ProductDTO> getProductDTOList(Iterable<Product> products);
}
