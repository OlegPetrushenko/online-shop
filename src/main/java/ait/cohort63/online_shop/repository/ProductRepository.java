package ait.cohort63.online_shop.repository;

import ait.cohort63.online_shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    // Поиск с игнорированием регистра в названии
    Optional<Product> findByTitleIgnoreCase(String title);
}
