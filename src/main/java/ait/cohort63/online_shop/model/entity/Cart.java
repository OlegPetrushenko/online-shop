package ait.cohort63.online_shop.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Cart cart)) return false;

        return Objects.equals(id, cart.id) && Objects.equals(customer, cart.customer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(customer);
        return result;
    }

    // Функционал корзины
    public void addProduct(Product product) {
        if (product.isActive()) products.add(product);
    }

    public List<Product> getAllActiveProducts() {
        return products.stream()
                .filter(Product::isActive)
                .toList();
    }

    public Product removeById(Long id) {
        Optional<Product> optionalProduct = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            products.remove(product);
            return product;
        }

        return null;
    }

    public void clear() {
        products.clear();
    }

    public BigDecimal getTotalPrice() {
        if (products == null || products.isEmpty()) return BigDecimal.ZERO;

        return products.stream()
                .filter(Product::isActive)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAveragePrice() {
        long countActive = products.stream()
                .filter(Product::isActive)
                .count();

        if (countActive == 0) return BigDecimal.ZERO;

        return getTotalPrice().divide(new BigDecimal(countActive), RoundingMode.HALF_UP);
    }
}
