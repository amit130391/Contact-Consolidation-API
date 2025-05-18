package com.assignment.IdentityReconciliation.Service;

import com.assignment.IdentityReconciliation.Dto.ContactResponseDto;
import com.assignment.IdentityReconciliation.Model.Contact;
import com.assignment.IdentityReconciliation.Model.LinkPrecedence;
import com.assignment.IdentityReconciliation.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public ContactResponseDto identify(String email, String phoneNumber) {

        // If both email and phoneNumber are null, reject
        if (email == null && phoneNumber == null) {
            throw new IllegalArgumentException("At least one of email or phoneNumber must be provided");
        }

        // Fetch existing contacts using custom query that ignores nulls
        List<Contact> matchedContacts = contactRepository.findByEmailOrPhoneNumberCustom(email, phoneNumber);
        Set<Contact> allLinkedContacts = new HashSet<>();

        // Collect all linked contacts
        for (Contact contact : matchedContacts) {
            allLinkedContacts.add(contact);
            Integer linkedId = contact.getLinkedId();
            if (linkedId != null) {
                allLinkedContacts.addAll(contactRepository.findByLinkedId(linkedId));
            } else {
                allLinkedContacts.addAll(contactRepository.findByLinkedId(contact.getId()));
            }
        }

        // Check if contact already exists with same email and phoneNumber
        boolean exactMatchExists = matchedContacts.stream().anyMatch(c ->
                Objects.equals(c.getEmail(), email) && Objects.equals(c.getPhoneNumber(), phoneNumber)
        );

        Contact primaryContact = allLinkedContacts.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .sorted(Comparator.comparing(Contact::getCreatedAt))
                .findFirst()
                .orElse(null);

        // If no matches found, insert a new primary contact
        if (matchedContacts.isEmpty()) {
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
            newContact.setCreatedAt(OffsetDateTime.now());
            newContact.setUpdatedAt(OffsetDateTime.now());
            contactRepository.save(newContact);
            allLinkedContacts.add(newContact);
            primaryContact = newContact;
        }
        // Insert a new secondary only if it's not an exact match
        else if (!exactMatchExists) {
            Contact secondary = new Contact();
            secondary.setEmail(email);
            secondary.setPhoneNumber(phoneNumber);
            secondary.setLinkPrecedence(LinkPrecedence.SECONDARY);
            secondary.setLinkedId(primaryContact.getId());
            secondary.setCreatedAt(OffsetDateTime.now());
            secondary.setUpdatedAt(OffsetDateTime.now());
            contactRepository.save(secondary);
            allLinkedContacts.add(secondary);
        }

        return buildResponse(primaryContact, allLinkedContacts);
    }

    private ContactResponseDto buildResponse(Contact primary, Set<Contact> contacts) {
        List<String> emails = contacts.stream()
                .map(Contact::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<String> phoneNumbers = contacts.stream()
                .map(Contact::getPhoneNumber)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<Integer> secondaryIds = contacts.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                .map(Contact::getId)
                .toList();

        ContactResponseDto.ContactInfo info = new ContactResponseDto.ContactInfo();
        info.setPrimaryContatctId(primary.getId());
        info.setEmails(new ArrayList<>(emails));
        info.setPhoneNumbers(new ArrayList<>(phoneNumbers));
        info.setSecondaryContactIds(new ArrayList<>(secondaryIds));

        ContactResponseDto response = new ContactResponseDto();
        response.setContact(info);
        return response;
    }
}
