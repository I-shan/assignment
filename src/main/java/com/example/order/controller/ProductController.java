package com.example.order.controller;


import com.example.order.dto.ProductDTO;
import com.example.order.entity.Product;
import com.example.order.exceptions.DeleteException;
import com.example.order.repo.ProductRepository;
import com.example.order.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductRepository repository;
    @Autowired
    ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer id) {
        ProductDTO dto = productService.getProduct(id);
        if (dto == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = productService.getProducts();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/list")
    public String getProductList(Model model) {
        Iterable<Product> products = repository.findAll();
        model.addAttribute("products", products);

        return "customers/products";
    }

    @GetMapping("/add")
    public String showNEwPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "customers/add";
    }

    @GetMapping("/save")
    public String addCustomer(Model model, @Valid @ModelAttribute("product") Product product, BindingResult br) {
        if (br.hasErrors()) {
            logger.error("addCustomer(): error happened in validation");
            return "customers/add";
        }
        repository.save(product);
        return getProductList(model);

    }


    @GetMapping("/update")
    public String showUpdatePage(Model model, @RequestParam("id") Integer Id) {
        Optional<Product> result = repository.findById(Id);
        result.ifPresent(product -> model.addAttribute("product", product));
        return "customers/add";
    }

    @PostMapping("/delete")
    public String deleteProduct(Model model, @RequestParam("id") Integer id) throws DeleteException {
        try {
            repository.deleteById(id);

            return getProductList(model);
        } catch (Exception ex) {
            throw new DeleteException("You can ony delete empty products");
        }
    }

    @GetMapping("/search")
    public String searchCustomer(Model model, @RequestParam("id") Integer Id, @RequestParam("name") String name) {
        List<Product> products = repository.findByProductIDOrName(Id, name);
        model.addAttribute("products", products);
        return "customers/products";
    }


}
