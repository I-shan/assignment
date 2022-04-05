package com.example.order.controller;

import com.example.order.dto.CustomerDTO;
import com.example.order.entity.Product;
import com.example.order.entity.Customer;
import com.example.order.repo.CustomerRepository;

import com.example.order.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer/")
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CustomerRepository repository;

    @Autowired
    com.example.order.repo.ProductRepository ProductRepository;
    @Autowired
    CustomerService customerService;

    @PostMapping("add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        String result = customerService.addCustomer(customer);
        if (result.contains("Error"))
            return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Integer id) {
        CustomerDTO customerDTO = customerService.getCustomer(id);
        if (customerDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        List<CustomerDTO> customerDTO = customerService.getCustomers();
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    @Transactional
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) {
        String result = customerService.updateCustomer(id, customer);
        if (result.contains("ERROR")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/")
    public String getIndex() {
        return "customers/index";
    }

    @GetMapping("/list")
    public String getCustomerList(Model model) {
        Iterable<Customer> customers = repository.findAll();
        model.addAttribute("customers", customers);
        return "customers/list";
    }


    @PostMapping("save")
    public String addCustomer(Model model, @Valid @ModelAttribute("customer") Customer customer, BindingResult br) {
        logger.debug("addCustomer() used");

        if (br.hasErrors()) {
            logger.error("addCustomer(): error happened in validation");
            return "customers/modify";
        }
        logger.info("addCustomer(): customer " + "customer" + " added");
        repository.save(customer);
        return getCustomerList(model);

    }


    @GetMapping("/modify")
    public String showNEwPage(Model model) {
        Customer st = new Customer();
        model.addAttribute("customer", st);
        return "customers/modify";
    }

    @GetMapping("/update")
    public String showUpdatePage(Model model, @RequestParam("id") Integer Id) {
        Optional<Customer> result = repository.findByCustomerId(Id);
        result.ifPresent(customer -> model.addAttribute("customer", customer));
        Iterable<Product> products = ProductRepository.findAll();
        model.addAttribute("allProducts", products);
        return "customers/modify";
    }

    @PostMapping("/delete")
    public String deleteCustomer(Model model, @RequestParam("id") Integer id) {
        repository.deleteById(id);
        return getCustomerList(model);
    }


    @GetMapping("/search")
    public String searchCustomer(Model model, @RequestParam("id") Integer Id, @RequestParam("first") String firstName, @RequestParam("last") String lastName) {
        List<Customer> customers = repository.findByCustomerIdOrFirstNameOrLastName(Id, firstName, lastName);
        model.addAttribute("customers", customers);
        return "customers/list";
    }
}

