package co.com.crediya.model.loantype;
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
public class LoanType {
    private  Long id_loan;
    private  String name;
    private BigDecimal min_amount;
    private BigDecimal max_amount;
    private  Double interest_rate;
    private Boolean auto_validation;

}
