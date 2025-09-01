package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.Customer;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.repository.CustomerRepository;
import ait.cohort63.online_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setActive(true);
        return repository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return repository.findAll().stream().filter(Customer::isActive).toList();
    }

    @Override
    public Customer getCustomerById(Long id) {
        Customer customer = repository.findById(id).orElse(null);

        if (customer == null || !customer.isActive()) {
            return null;
        }

        return customer;
    }

    @Override
    public Customer updateCustomer(Long id) {
        return null;
    }

    @Override
    public Customer deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public Customer deleteCustomerByName(String name) {
        return null;
    }

    @Override
    public Customer restoreCustomerById(Long id) {
        return null;
    }

    @Override
    public long getCustomerCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice(Long id) {
        return null;
    }

    @Override
    public Product addProductById(Long customerId, Long productId) {
        return null;
    }

    @Override
    public Product deleteProductById(Long customerId, Long productId) {
        return null;
    }

    @Override
    public void clearCart(Long id) {

    }
}
