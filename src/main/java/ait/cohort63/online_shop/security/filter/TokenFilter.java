package ait.cohort63.online_shop.security.filter;

import ait.cohort63.online_shop.security.AuthInfo;
import ait.cohort63.online_shop.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // Извлекаем токен из заголовка Authorization
        String accessToken = getTokenFromRequest(httpServletRequest);

        if (accessToken != null && tokenService.validateAccessToken(accessToken)) {
            // Извлекаем информацию о пользователе из токена
            Claims claims = tokenService.getAccessClaims(accessToken);
            // Конвертируем Claim -> AuthInfo (чтобы Spring Security смог работать с этим объектом)
            AuthInfo authInfo = tokenService.mapClaimsToAuthInfo(claims);
            // Устанавливаем пользователя как аутентифицированного
            authInfo.setAuthenticated(true);
            // Помещаем объект AuthInfo в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authInfo);
        }

        // Продолжить цепочку фильтров
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Headers:
    // Authorization: Bearer token

    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        // Извлекаем значение в заголовке Authorization -> Bearer <token>
        String bearerToken = httpServletRequest.getHeader("Authorization");

        // Проверяем, что заголовок не пустой и начинается с фразы "Bearer "
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Обрезаем префикс "Bearer" и возвращаем сам токен
            return bearerToken.substring(7);
        }

        // Если заголовок пустой и не начинается с "Bearer"
        return null;
    }
}
