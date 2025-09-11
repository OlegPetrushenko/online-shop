package ait.cohort63.online_shop.repository;

import ait.cohort63.online_shop.model.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    Optional<ConfirmationCode> findByCode(String code);
}
