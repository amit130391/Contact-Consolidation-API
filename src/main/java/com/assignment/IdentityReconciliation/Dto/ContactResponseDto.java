package com.assignment.IdentityReconciliation.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ContactResponseDto {
    private ContactInfo contact;

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    @Data
    public static class ContactInfo {
        private Integer primaryContatctId;
        private List<String> emails;
        private List<String> phoneNumbers;
        private List<Integer> secondaryContactIds;

        public Integer getPrimaryContatctId() {
            return primaryContatctId;
        }

        public void setPrimaryContatctId(Integer primaryContatctId) {
            this.primaryContatctId = primaryContatctId;
        }

        public List<String> getEmails() {
            return emails;
        }

        public void setEmails(List<String> emails) {
            this.emails = emails;
        }

        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }

        public void setPhoneNumbers(List<String> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        public List<Integer> getSecondaryContactIds() {
            return secondaryContactIds;
        }

        public void setSecondaryContactIds(List<Integer> secondaryContactIds) {
            this.secondaryContactIds = secondaryContactIds;
        }
    }
}
