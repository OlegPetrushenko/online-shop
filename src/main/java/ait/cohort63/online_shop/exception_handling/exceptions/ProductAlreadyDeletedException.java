package ait.cohort63.online_shop.exception_handling.exceptions;

public class ProductAlreadyDeletedException extends  RuntimeException {

    public ProductAlreadyDeletedException(String message) {
        super(message);
    }
}
