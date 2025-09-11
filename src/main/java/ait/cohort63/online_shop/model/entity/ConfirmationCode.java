package ait.cohort63.online_shop.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirm_code")
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, length = 36)
    private String code;

    @Column(name = "expired", nullable = false)
    private LocalDateTime expired;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmationCode(String code, LocalDateTime expired, User user) {
        this.code = code;
        this.expired = expired;
        this.user = user;
    }

    public ConfirmationCode() {
    }

    @Override
    public String toString() {
        return String.format("ConfirmCode id - %d, code - %s, user: %s, expired - %s",
                id, code,
                user == null ? "empty" : user.getUsername(),
                expired
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ConfirmationCode that)) return false;

        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(expired, that.expired) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(code);
        result = 31 * result + Objects.hashCode(expired);
        result = 31 * result + Objects.hashCode(user);
        return result;
    }
}
