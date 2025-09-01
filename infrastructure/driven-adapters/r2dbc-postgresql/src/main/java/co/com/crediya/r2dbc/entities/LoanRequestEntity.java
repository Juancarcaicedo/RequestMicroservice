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
@Table("loan_request")
public class LoanRequestEntity {
    @Id
    @Column("id")
    private  Long idRequest;
    @Column("amount")
    private BigDecimal amount;
    @Column("term_in_months")
    private  Integer termInMonths;
    @Column("email")
    private   String email;
    @Column("loan_type_id")
    private  Long loanTypeId;
    @Column("status_id")
    private  Long statusId;
    @Column("document")
    private  String document;


}
