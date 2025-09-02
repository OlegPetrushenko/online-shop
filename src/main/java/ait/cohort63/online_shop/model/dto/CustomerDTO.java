package ait.cohort63.online_shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

@Schema(description = "DTO for Customer")
public class CustomerDTO {

    @Schema(description = "Customer unique identifier", example = "333", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Customer name", example = "Richard")
    private String name;

    public CustomerDTO() {
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

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s",
                id, name);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof CustomerDTO that)) return false;

        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        return result;
    }
}
