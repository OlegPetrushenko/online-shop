package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.dto.UserRegisterDTO;
import ait.cohort63.online_shop.model.entity.User;
import ait.cohort63.online_shop.repository.UserRepository;
import ait.cohort63.online_shop.service.interfaces.EmailService;
import ait.cohort63.online_shop.service.interfaces.RoleService;
import ait.cohort63.online_shop.service.interfaces.UserService;
import ait.cohort63.online_shop.service.mapping.UserMapperService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;
    private final UserMapperService mapper;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder encoder, EmailService emailService, UserMapperService mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.emailService = emailService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO) {

        User user = mapper.mapDtoToEntity(userRegisterDTO);

        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());

        // Проверка существования пользователя с такими email
        if (userRepository.existsByEmail(user.getEmail()) && optionalUser.get().isActive()) {
            throw new RuntimeException("Email is already in use");
        }

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            /*
            Если мы не хотим менять пароль существующему пользователю
            user.setUsername((userRegisterDTO.username())); еще опция
            emailService.sendConfirmationEmail(user);
            return;
             */
        } else {
            user.setRoles(Set.of(roleService.getRoleUser()));
        }

        // два сценария работы с паролями - предусматриваем, что
        user.setPassword(encoder.encode(userRegisterDTO.password()));

        user.setActive(false);

        userRepository.save(user);

        // после успешного сохранения отправляем email с кодом подтверждения
        emailService.sendConfirmationEmail(user);
    }
}
