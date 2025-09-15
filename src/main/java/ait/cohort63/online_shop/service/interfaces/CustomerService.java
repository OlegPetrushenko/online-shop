package ait.cohort63.online_shop.service.interfaces;

import ait.cohort63.online_shop.model.dto.CustomerDTO;
import ait.cohort63.online_shop.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllActiveCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    CustomerDTO deleteCustomerById(Long id);

    CustomerDTO deleteCustomerByName(String name);

    CustomerDTO restoreCustomerById(Long id);

    long getActiveCustomerCount();

    BigDecimal getCartTotalPrice(Long customerId);

    BigDecimal getCartAveragePrice(Long customerId);

    Product addProductToCart(Long customerId, Long productId);

    Product removeProductFromCart(Long customerId, Long productId);

    void clearCustomerCart(Long customerId);
}
