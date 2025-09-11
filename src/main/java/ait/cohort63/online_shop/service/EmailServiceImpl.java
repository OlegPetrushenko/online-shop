package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.User;
import ait.cohort63.online_shop.service.interfaces.ConfirmationCodeService;
import ait.cohort63.online_shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    // Импорт freemarker.template.Configuration
    private final Configuration mailConfig;

    private final ConfirmationCodeService confirmationService;

    private final static String HOST = "http://localhost:8080/api";

    public EmailServiceImpl(JavaMailSender mailSender, Configuration configuration, ConfirmationCodeService confirmationService) {
        this.mailSender = mailSender;
        this.mailConfig = configuration;
        this.confirmationService = confirmationService;

        // Настройка кодировки и расположение шаблонов
        this.mailConfig.setDefaultEncoding("UTF-8");
        this.mailConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/mail"));
    }

    @Override
    public void sendConfirmationEmail(User user) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Адрес отправителя. Получаем из переменной среды
            String fromAddress = System.getenv("MAIL_USERNAME");

            // Указываем отправителя
            helper.setFrom(fromAddress);

            // Получатель
            helper.setTo(user.getEmail());

            // Тема
            helper.setSubject("registration Confirmation");

            String emailText = generateEmailText(user);

            // Добавление текста письма
            helper.setText(emailText, true);

            // Отправка письма
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateEmailText(User user) {

        try {
            // Загрузка шаблона
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");

            // Генерация и сохранение кода подтверждения для пользователя
            String code = confirmationService.generationConfirmationCode(user);

            // Сформировать ссылку для подтверждения регистрации
            // http://localhost:8080/api/confirm?code=значение_кода
            String confirmationLink = HOST + "/confirm?code=" + code;

            // Вставить данные пользователя (имя и ссылку)
            // Model - это специальный объект:
            // {
            // name : value,
            // confirmationLink : value
            // }
            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());
            model.put("confirmationLink", confirmationLink);

            // Генерация и возврат текста письма
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
