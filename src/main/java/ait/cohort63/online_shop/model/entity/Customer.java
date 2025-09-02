package ait.cohort63.online_shop.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

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

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %s",
                id, name, active ? "yes" : "no");
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;

        return active == customer.active && id.equals(customer.id) && name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + Boolean.hashCode(active);
        return result;
    }
}
