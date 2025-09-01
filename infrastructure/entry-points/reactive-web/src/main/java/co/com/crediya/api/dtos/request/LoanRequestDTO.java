package co.com.crediya.api.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Schema(description = "Datos para registrar una solicitud de préstamo")
public class LoanRequestDTO {

    //@Schema(description = "Documento de identidad del cliente", example = "123456789")
    @NotBlank(message = "El documento es obligatorio")
    private String document;

   // @Schema(description = "Correo electrónico del cliente", example = "juan@email.com")
    @Email(message = "Formato inválido")
    private String email;

    //@Schema(description = "Monto solicitado", example = "5000000")
    @DecimalMin(value = "100000", message = "Monto mínimo: 100,000")
    @DecimalMax(value = "15000000", message = "Monto máximo: 15,000,000")
    private BigDecimal amount;

   // @Schema(description = "Plazo en meses", example = "24")
    @Min(value = 6, message = "Plazo mínimo: 6 meses")
    @Max(value = 60, message = "Plazo máximo: 60 meses")
    private Integer termInMonths;

    //@Schema(description = "ID del tipo de préstamo", example = "1")
    @NotNull(message = "El tipo de préstamo es obligatorio")
    private Long loanTypeId;
}
