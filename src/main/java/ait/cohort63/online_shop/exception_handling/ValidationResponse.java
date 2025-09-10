package ait.cohort63.online_shop.exception_handling;

import java.util.List;
import java.util.Objects;

public class ValidationResponse {

    private List<String> errors;

    public ValidationResponse(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Response: errors - " + errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ValidationResponse that)) return false;

        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(errors);
    }
}
