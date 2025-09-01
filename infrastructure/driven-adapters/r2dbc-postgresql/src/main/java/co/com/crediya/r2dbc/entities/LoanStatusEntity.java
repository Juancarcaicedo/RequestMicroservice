package co.com.crediya.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("loan_status")
public class LoanStatusEntity {
    @Id
    @Column("id")
    private  Long idStatus;
    @Column("name")
    private  String name;
    @Column("description")
    private  String description;
}
