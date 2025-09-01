package co.com.crediya.api.dtos.response;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Schema(description = "Respuesta al registrar una solicitud de préstamo")
public class LoanRequestResponseDTO {

  //  @Schema(description = "ID de la solicitud registrada", example = "1001")
    private Long id;

   // @Schema(description = "Documento del cliente", example = "123456789")
    private String document;

    //@Schema(description = "Correo electrónico del cliente", example = "juan@email.com")
    private String email;

    //@Schema(description = "Monto solicitado", example = "5000000")
    private BigDecimal amount;

    //@Schema(description = "Plazo en meses", example = "24")
    private Integer termInMonths;

   // @Schema(description = "ID del tipo de préstamo", example = "1")
    private Long loanTypeId;

    //@Schema(description = "ID del estado actual de la solicitud", example = "1")
    private Long statusId;
}
