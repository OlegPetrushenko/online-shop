package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.ConfirmationCode;
import ait.cohort63.online_shop.model.entity.User;
import ait.cohort63.online_shop.repository.ConfirmationCodeRepository;
import ait.cohort63.online_shop.repository.UserRepository;
import ait.cohort63.online_shop.service.interfaces.ConfirmationCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private final ConfirmationCodeRepository codeRepository;

    private final UserRepository userRepository;

    public ConfirmationCodeServiceImpl(ConfirmationCodeRepository codeRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
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

        codeRepository.save(confirmationCode); // Сохранение кода в базу данных

        return code; // Возвращаем сгенерированный код
    }

    @Override
    @Transactional
    public void confirmUser(String code) {

        ConfirmationCode confirmationCode = codeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid confirmation code"));

        if (confirmationCode.getExpired().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Confirmation code expired");
        }

        User user = confirmationCode.getUser();

        if (user.isActive()) {
            throw new RuntimeException("User already activated");
        }

        // Активация пользователя
        user.setActive(true);
        userRepository.save(user);
    }
}
