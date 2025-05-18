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

//    List<Contact> allContacts = contactRepository.findAll();

    @Transactional
    public ContactResponseDto identify(String email, String phoneNumber) {
        List<Contact> contacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);
        Set<Contact> allLinked = new HashSet<>();

        for (Contact c : contacts) {
            allLinked.add(c);
            if (c.getLinkedId() != null)
                allLinked.addAll(contactRepository.findByLinkedId(c.getLinkedId()));
            else
                allLinked.addAll(contactRepository.findByLinkedId(c.getId()));
        }

        Optional<Contact> primary = allLinked.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .sorted(Comparator.comparing(Contact::getCreatedAt))
                .findFirst();

        Contact primaryContact = primary.orElse(null);

        if (primaryContact == null) {
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
            newContact.setCreatedAt(OffsetDateTime.now());
            newContact.setUpdatedAt(OffsetDateTime.now());
            contactRepository.save(newContact);
            primaryContact = newContact;
        }
        else if (allLinked.stream()
                .noneMatch(c -> Objects.equals(c.getEmail(), email) && Objects.equals(c.getPhoneNumber(), phoneNumber))) {
            Contact secondary = new Contact();
            secondary.setEmail(email);
            secondary.setPhoneNumber(phoneNumber);
            secondary.setLinkPrecedence(LinkPrecedence.SECONDARY);
            secondary.setLinkedId(primaryContact.getId());
            secondary.setCreatedAt(OffsetDateTime.now());
            secondary.setUpdatedAt(OffsetDateTime.now());
            contactRepository.save(secondary);
            allLinked.add(secondary);
        }

        List<String> emails = allLinked.stream().map(Contact::getEmail).filter(Objects::nonNull).distinct().toList();
        List<String> phones = allLinked.stream().map(Contact::getPhoneNumber).filter(Objects::nonNull).distinct().toList();
        List<Integer> secondaryIds = allLinked.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                .map(Contact::getId).toList();

        ContactResponseDto response = new ContactResponseDto();
        ContactResponseDto.ContactInfo info = new ContactResponseDto.ContactInfo();
        info.setPrimaryContatctId(primaryContact.getId());
        info.setEmails(emails);
        info.setPhoneNumbers(phones);
        info.setSecondaryContactIds(secondaryIds);
        response.setContact(info);

        return response;

    }



}
