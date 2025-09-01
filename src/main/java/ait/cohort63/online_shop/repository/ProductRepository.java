package ait.cohort63.online_shop.repository;

import ait.cohort63.online_shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
