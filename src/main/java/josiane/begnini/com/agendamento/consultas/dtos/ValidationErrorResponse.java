package josiane.begnini.com.agendamento.consultas.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse extends ApiError {
    private List<FieldError> fieldErrors = new ArrayList<>();

    public ValidationErrorResponse() {}

    public void addFieldError(String field, String message) {
        this.fieldErrors.add(new FieldError(field, message));
    }

}