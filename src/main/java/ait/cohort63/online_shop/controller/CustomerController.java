package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.model.dto.CustomerDTO;
import ait.cohort63.online_shop.model.dto.ProductDTO;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// http://localhost:8080/api/customers с префиксом для запросов к бек энду (настроено в application.yml)

@RestController
@RequestMapping("/customers") // Указывает, что контроллер обрабатывает запросы связанные с ресурсом customers
@Tag(name = "Customer controller", description = "Controller for operations with customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create customer", description = "Add new customer.", tags = {"Customer"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class))})})
    @PostMapping
    public CustomerDTO saveCustomer(@Parameter(description = "Created customer object") @RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @Operation(summary = "Get customer by id", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping("/{id}")
    public CustomerDTO getById(@Parameter(description = "The id that needs to be fetched", required = true) @PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Get all customers", tags = {"Customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.getAllActiveCustomers();
    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
//        customerDTO.setId(id);
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public CustomerDTO remove(@PathVariable Long id) {
        return customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/{name}")
    public CustomerDTO removeByName(@PathVariable String name) {
        return customerService.deleteCustomerByName(name);
    }

    @PostMapping("/{id}/restore")
    public CustomerDTO restore(@PathVariable Long id) {
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
