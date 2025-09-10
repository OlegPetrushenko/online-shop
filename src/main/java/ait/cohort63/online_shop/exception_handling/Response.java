package ait.cohort63.online_shop.exception_handling;

import java.util.Objects;

public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response: message - " + message;
    }

    public String getMassage() {
        return message;
    }

    public void setMassage(String massage) {
        this.message = massage;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Response response)) return false;

        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
