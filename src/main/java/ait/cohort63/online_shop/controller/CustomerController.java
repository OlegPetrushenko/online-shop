package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.model.entity.Customer;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.service.interfaces.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// http://localhost:8080/api/customers с префиксом для запросов к бек энду (настроено в application.yml)

@RestController
@RequestMapping("/customers") // Указывает, что контроллер обрабатывает запросы связанные с ресурсом customers
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAllActiveCustomers();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public Customer remove(@PathVariable Long id) {
        return customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/{name}")
    public Customer removeByName(@PathVariable String name) {
        return customerService.deleteCustomerByName(name);
    }

    @PostMapping("/{id}/restore")
    public Customer restore(@PathVariable Long id) {
        return customerService.restoreCustomerById(id);
    }

    @GetMapping("/count")
    public long getCustomerCount() {
        return customerService.getCustomerCount();
    }

    @GetMapping("/{id}/total")
    public BigDecimal getTotalPrice(@PathVariable Long id) {
        return customerService.getTotalPrice(id);
    }

    @GetMapping("/{id}/average")
    public BigDecimal getAveragePrice(@PathVariable Long id) {
        return customerService.getAveragePrice(id);
    }

    @PostMapping("/{customerId}/cart/{productId}")
    public Product addProductToCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return customerService.addProductById(customerId, productId);
    }

    @DeleteMapping("/{customerId}/cart/{productId}")
    public Product deleteProductFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return customerService.deleteProductById(customerId, productId);
    }

    @DeleteMapping("/{id}/cart")
    public ResponseEntity<String> clearCart(@PathVariable Long id) {
        customerService.clearCart(id);
        return ResponseEntity.ok("Cart cleared for customer with ID: " + id);
    }
}
