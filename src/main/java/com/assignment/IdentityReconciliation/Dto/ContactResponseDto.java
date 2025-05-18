package com.assignment.IdentityReconciliation.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponseDto {

    private ContactInfo contact;

    @Data
    public static class ContactInfo {
        private Integer primaryContatctId;
        private List<String> emails;
        private List<String> phoneNumbers;
        private List<Integer> secondaryContactIds;
    }
}
