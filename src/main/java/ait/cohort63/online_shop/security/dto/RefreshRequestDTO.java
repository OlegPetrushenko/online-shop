package ait.cohort63.online_shop.security.dto;

import java.util.Objects;

public class RefreshRequestDTO {

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "Refresh request -> refresh token: " + refreshToken;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof RefreshRequestDTO that)) return false;

        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(refreshToken);
    }
}
