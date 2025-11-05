package josiane.begnini.com.agendamento.consultas.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import josiane.begnini.com.agendamento.consultas.dtos.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * Responsável por interceptar as exceções da aplicação
 * e retornar respostas HTTP adequadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // --- Tratamento de validações ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setError("Validation Failed");
        errors.setMessage("Um ou mais campos são inválidos");
        errors.setPath(request.getRequestURI());

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.addFieldError(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + ex.getMessage());
    }
}