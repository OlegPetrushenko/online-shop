package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.dto.CustomerDTO;
import ait.cohort63.online_shop.model.entity.Customer;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.repository.CustomerRepository;
import ait.cohort63.online_shop.service.interfaces.CustomerService;
import ait.cohort63.online_shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    private final CustomerMappingService mapper;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.mapDtoToEntity(customerDTO);
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

        if (customer == null || !customer.isActive()) {
            return null;
        }

        return mapper.mapEntityToDto(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerByName(String name) {
        return null;
    }

    @Override
    public CustomerDTO restoreCustomerById(Long id) {
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
