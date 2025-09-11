package ait.cohort63.online_shop.repository;

import ait.cohort63.online_shop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);
}
