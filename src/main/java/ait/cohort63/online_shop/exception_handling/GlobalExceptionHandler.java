package ait.cohort63.online_shop.exception_handling;

import ait.cohort63.online_shop.exception_handling.exceptions.ProductDeletionException;
import ait.cohort63.online_shop.exception_handling.exceptions.ProductAlreadyDeletedException;
import ait.cohort63.online_shop.exception_handling.exceptions.ProductNotFoundException;
import ait.cohort63.online_shop.exception_handling.exceptions.ThirdTestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleThirdException(ThirdTestException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductDeletionException.class)
    public ResponseEntity<Response> handleProductDeletion(ProductDeletionException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleProductNotFound(ProductNotFoundException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyDeletedException.class)
    public ResponseEntity<Response> handleProductAlreadyDeleted(ProductAlreadyDeletedException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    //    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex) {
        // Вариант 1
        // Логика обработки ошибок валидации
        // Поля с ошибками хранятся в объекте BindingResult
        // FieldError - класс. Ошибка связанная с конкретным полем

        // Создаем StringBuilder для накопления сообщений об ошибках
        StringBuilder errorMessage = new StringBuilder();

        // Перебираем все ошибки валидации
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Добавляем сообщение об ошибки для текущего поля
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }
        // Создаем объект Respons с накопленным сообщение об ошибках
        Response response = new Response(errorMessage.toString());
        // Возвращаем ResponseEntity с объектом Response и статусом BAD_REQUEST
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleValidationExceptionV2(MethodArgumentNotValidException ex) {
        // Логика обработки ошибок валидации
        // Поля с ошибками хранятся в объекте BindingResult
        // FieldError - класс. Ошибка связанная с конкретным полем

        // Создаем список ошибок для накопления сообщений об ошибках
        List<String> errors = new ArrayList<>();

        // Перебираем все ошибки валидации
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Добавляем сообщение об ошибки для текущего поля
            errors.add(error.getField() + " (" + error.getRejectedValue() + ")" + " -> " + error.getDefaultMessage());
        }
        // Создаем объект Respons с накопленным сообщение об ошибках
        ValidationResponse response = new ValidationResponse(errors);
        // Возвращаем ResponseEntity с объектом Response и статусом BAD_REQUEST
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
