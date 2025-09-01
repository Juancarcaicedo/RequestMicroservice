package co.com.crediya.model.loanrequest;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanRequest {
  private  Long idRequest;
  private BigDecimal amount;
  private  Integer termInMonths;
  private   String email;
  private  Long loanTypeId;
  private  Long statusId;
  private  String document;
}
