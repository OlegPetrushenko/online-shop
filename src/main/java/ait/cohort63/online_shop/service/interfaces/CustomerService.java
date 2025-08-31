package ait.cohort63.online_shop.service.interfaces;

import ait.cohort63.online_shop.model.entity.Customer;
import ait.cohort63.online_shop.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllActiveCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id);

    Customer deleteCustomerById(Long id);

    Customer deleteCustomerByName(String name);

    Customer restoreCustomerById(Long id);

    long getCustomerCount();

    BigDecimal getTotalPrice(Long id);

    BigDecimal getAveragePrice(Long id);

    Product addProductById(Long customerId, Long productId);

    Product deleteProductById(Long customerId, Long productId);

    void clearCart(Long id);
}
