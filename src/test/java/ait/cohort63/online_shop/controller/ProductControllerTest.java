package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.model.dto.ProductDTO;
import ait.cohort63.online_shop.model.entity.Role;
import ait.cohort63.online_shop.model.entity.User;
import ait.cohort63.online_shop.repository.RoleRepository;
import ait.cohort63.online_shop.repository.UserRepository;
import ait.cohort63.online_shop.security.dto.LoginRequestDTO;
import ait.cohort63.online_shop.security.dto.TokenResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    private static final String TEST_PRODUCT_TITLE = "Test Product";
    private static final int TEST_PRODUCT_PRICE = 777;

    private static final String TEST_ADMIN_NAME = "Test Admin";
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_PASSWORD = "Test password";

    private static final String ROLE_ADMIN_TITLE = "ROLE_ADMIN";
    private static final String ROLE_USER_TITLE = "ROLE_USER";

    private final String URL_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/api/auth";
    private final String PRODUCT_RESOURCE_NAME = "/api/products";
    private final String LOGIN_ENDPOINT = "/login";

    private final String BEARER_PREFIX = "Bearer ";

    @LocalServerPort
    private int port;

    // для работы с http запросами
    private TestRestTemplate template;

    // import org.springframework.http.HttpHeaders;
    private HttpHeaders headers;

    private ProductDTO testProduct;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private String adminAccessToken;
    private String userAccessToken;

    @BeforeEach
    public void setUp() {
        // Инициализация TestRestTemplate
        template = new TestRestTemplate();
        // Инициализация заголовков
        headers = new HttpHeaders();
        // Создание (инициализация) тестового продукта
        testProduct = new ProductDTO();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(new BigDecimal(TEST_PRODUCT_PRICE));

        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            // Надо создать и сохранить в БД админа
            roleAdmin = roleRepository.findByTitle(ROLE_ADMIN_TITLE)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found in DB"));
            roleUser = roleRepository.findByTitle(ROLE_USER_TITLE)
                    .orElseThrow(() -> new RuntimeException("Role USER not found in DB"));

            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }

        if (user == null) {
            // Надо создать и сохранить в БД юзера
            roleUser = (roleUser == null) ? roleRepository.findByTitle(ROLE_USER_TITLE)
                    .orElseThrow(() -> new RuntimeException("Role USER not found in DB")) : roleUser;

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            userRepository.save(user);
        }

        // POST -> http://localhost:port/api/auth/login + LoginRequestDTO
        // Создаем объекты LogiRequestDTO для админа и пользователя
        LoginRequestDTO loginAdminDto = new LoginRequestDTO(TEST_ADMIN_NAME, TEST_PASSWORD);
        LoginRequestDTO loginUserDto = new LoginRequestDTO(TEST_USER_NAME, TEST_PASSWORD);

        String authUrl = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginAdminDto);

        ResponseEntity<TokenResponseDTO> response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDTO.class
        );

        assertTrue(response.hasBody(), "Authorisation (admin) response body is empty");

        TokenResponseDTO tokenResponse = response.getBody();
        adminAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();

        request = new HttpEntity<>(loginUserDto);

        response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDTO.class
        );

        assertTrue(response.hasBody(), "Authorisation (user) response body is empty");

        tokenResponse = response.getBody();
        userAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();
    }

    // Тесты будут здесь

    @Test
    public void test() {

    }
}