package josiane.begnini.com.agendamento.consultas.dtos;

import lombok.Data;

@Data
public class FieldError {
    private String field;
    private String message;

    public FieldError() {}

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}