package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.dto.CustomerDTO;
import ait.cohort63.online_shop.model.entity.Cart;
import ait.cohort63.online_shop.model.entity.Customer;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.repository.CustomerRepository;
import ait.cohort63.online_shop.repository.ProductRepository;
import ait.cohort63.online_shop.service.interfaces.CustomerService;
import ait.cohort63.online_shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMappingService mapper;
    private final ProductRepository productRepository;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mapper, ProductRepository productRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.mapDtoToEntity(customerDTO);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);

        return mapper.mapEntityToDto(repository.save(customer));
    }

    @Override
    public List<CustomerDTO> getAllActiveCustomers() {
        return repository.findAll().stream()
                .filter(Customer::isActive)
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = repository.findById(id).orElse(null);

        if (customer != null && customer.isActive()) {
            return mapper.mapEntityToDto(customer);
        }

        throw new RuntimeException("Customer with id " + id + " not found");
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        return null;
    }

    @Transactional
    @Override
    public CustomerDTO deleteCustomerById(Long id) {
        Optional<Customer> customerOptional = repository.findById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setActive(false);
        } else throw new RuntimeException("Customer with id " + id + " not found");

        return null;
    }

    @Transactional
    @Override
    public CustomerDTO deleteCustomerByName(String name) {
        Optional<Customer> customerOptional = repository.findByName(name);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with name " + name + " not found"));
        customer.setActive(false);

        return null;
    }

    @Override
    public CustomerDTO restoreCustomerById(Long id) {
        Optional<Customer> optionalCustomer = repository.findById(id);

        Customer customer = optionalCustomer.orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        customer.setActive(true);

        return mapper.mapEntityToDto(customer);
    }

    @Override
    public long getActiveCustomerCount() {
        return repository.findAll().stream()
                .filter(Customer::isActive)
                .count();
    }

    @Override
    public BigDecimal getCartTotalPrice(Long customerId) {
        Optional<Customer> customerOptional = repository.findById(customerId);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));
        Cart cart = customer.getCart();

        return cart.getTotalPrice();
    }

    @Override
    public BigDecimal getCartAveragePrice(Long customerId) {
        Optional<Customer> customerOptional = repository.findById(customerId);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));

        return customer.getCart().getAveragePrice();
    }

    @Transactional
    @Override
    public Product addProductToCart(Long customerId, Long productId) {
        Optional<Customer> customerOptional = repository.findById(customerId);

        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer with id " + customerId + " not found"));

        Optional<Product> productOptional = productRepository.findById(productId);

        Product product = productOptional.orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found"));

        customer.getCart().addProduct(product);

        return null;
    }

    @Override
    public Product removeProductFromCart(Long customerId, Long productId) {
        return null;
    }

    @Override
    public void clearCustomerCart(Long customerId) {

    }
}
