package ait.cohort63.online_shop.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

public class CartDTO {

    private Long id;
    @JsonBackReference
    private CustomerDTO customer;
    private List<ProductDTO> products;

    @Override
    public String toString() {
        return String.format("Cart: id - %d, products count: %d",
                id, products == null ? 0 : products.size());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof CartDTO cartDTO)) return false;

        return Objects.equals(id, cartDTO.id) && Objects.equals(customer, cartDTO.customer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(customer);
        return result;
    }
}
