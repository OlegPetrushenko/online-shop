package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.ConfirmationCode;
import ait.cohort63.online_shop.model.entity.User;
import ait.cohort63.online_shop.repository.ConfirmationCodeRepository;
import ait.cohort63.online_shop.service.interfaces.ConfirmationCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    public ConfirmationCodeServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generationConfirmationCode(User user) {

        // Генерация уникального кода
        String code = UUID.randomUUID().toString();

        // Создание объекта ConfirmationCode и сохранение в БД
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setCode(code);
        confirmationCode.setUser(user);
        confirmationCode.setExpired(LocalDateTime.now().plusDays(1)); // Срок действия 1 день
//        confirmationCode.setExpired(LocalDateTime.now().plusMinutes(2));

        repository.save(confirmationCode); // Сохранение кода в базу данных

        return code; // Возвращаем сгенерированный код

    }
}
