package ait.cohort63.online_shop.exception_handling;

import java.util.Objects;

public class Response {

    private String message;
    private String url;

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, String url) {
        this.message = message;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        return Objects.equals(message, response.message) && Objects.equals(url, response.url);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(message);
        result = 31 * result + Objects.hashCode(url);
        return result;
    }
}
