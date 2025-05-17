package com.assignment.IdentityReconciliation.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactWrapperResponse {
    private ContactResponseDto contact;
}
