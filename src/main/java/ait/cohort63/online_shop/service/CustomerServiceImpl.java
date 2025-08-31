package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.Customer;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return List.of();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return null;
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
