package ait.cohort63.online_shop.repository;

import ait.cohort63.online_shop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
