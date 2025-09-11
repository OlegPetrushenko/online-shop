package ait.cohort63.online_shop.service.interfaces;

import ait.cohort63.online_shop.model.entity.User;

public interface EmailService {

    // Метод отправки письма с кодом подтверждения
    void sendConfirmationEmail(User user);
}
