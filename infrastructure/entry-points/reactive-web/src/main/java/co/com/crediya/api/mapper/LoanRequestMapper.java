package co.com.crediya.api.mapper;

import co.com.crediya.api.dtos.request.LoanRequestDTO;
import co.com.crediya.api.dtos.response.LoanRequestResponseDTO;
import co.com.crediya.model.loanrequest.LoanRequest;
import org.springframework.stereotype.Component;

@Component
public class LoanRequestMapper {
    public LoanRequest toDomain(LoanRequestDTO dto) {
        return LoanRequest.builder()
                .amount(dto.getAmount())
                .termInMonths(dto.getTermInMonths())
                .email(dto.getEmail())
                .document(dto.getDocument())
                .loanTypeId(dto.getLoanTypeId())
                .build();
    }

    public LoanRequestResponseDTO toResponse(LoanRequest domain) {
        return LoanRequestResponseDTO.builder()
                .id(domain.getLoanTypeId())
                .email(domain.getEmail())
                .document(domain.getDocument())
                .amount(domain.getAmount())
                .termInMonths(domain.getTermInMonths())
                .loanTypeId(domain.getLoanTypeId())
                .statusId(domain.getStatusId())
                .build();
    }
}
