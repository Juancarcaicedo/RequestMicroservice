package co.com.crediya.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("loan_type")
public class LoanTypeEntity {
    @Id
    @Column("id")
    private  Long idLoan;
    @Column("name")
    private  String name;
    @Column("min_amount")
    private BigDecimal minAmount;
    @Column("max_amount")
    private BigDecimal maxAmount;
    @Column("interest_rate")
    private  Double interestRate;
    @Column("auto_validation")
    private Boolean autoValidation;
}
