package ait.cohort63.online_shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "DTO for Product")
public class ProductDTO {

    @Schema(description = "Product unique identifier", example = "777", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product title", example = "Banana")
    @NotNull(message = "Product title cannot be null")
    // @NotEmpty проверяет, что поле не null и что оно не пустое (может состоять из пробелов)
    @NotBlank(message = "Product title cannot be empty")
//    @Pattern(regexp = "^[A-Z][a-z ]{2,}$", message = "")
    // Первая большая, вторая маленькая, третья обязательная и последующие любая буква, цифра или пробел
    @Pattern(regexp = "^[A-Z][a-z][a-zA-Z0-9 ]+$", message = "Product title should be at least 3 character, start with a capital letter, and contains only letters, digits and spaces")
    private String title;

    @Schema(description = "Product price", example = "8.50")
    @DecimalMin(value = "1.0", message = "Product price should be greater or equal than 1.0")
    @DecimalMax(value = "100000", inclusive = false, message = "Product price should be less than 100_000")
    private BigDecimal price;

    private String image;

    public ProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s, image - %s",
                id, title, price, image);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ProductDTO that)) return false;

        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Objects.hashCode(image);
        return result;
    }
}
