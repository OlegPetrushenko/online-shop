package ait.cohort63.online_shop.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customer")
@Schema(description = "Class that describes Customer")
public class Customer {

    @Schema(description = "Customer unique identifier", example = "333", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // если имена одинаковые, значение в скобках можно не писать
    private Long id;

    @Schema(description = "Customer name", example = "Richard")
    @Column
    private String name;

    @Schema(description = "Id customer available", accessMode = Schema.AccessMode.READ_ONLY)
    @Column
    private boolean active;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s, Cart - %s",
                id, name, active ? "yes" : "no", cart == null ? "null" : cart);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;

        return active == customer.active && Objects.equals(id, customer.id) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Boolean.hashCode(active);
        return result;
    }
}
