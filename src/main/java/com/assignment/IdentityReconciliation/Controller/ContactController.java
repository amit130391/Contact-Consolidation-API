package com.assignment.IdentityReconciliation.Controller;

import com.assignment.IdentityReconciliation.Dto.ContactRequestDto;
import com.assignment.IdentityReconciliation.Dto.ContactResponseDto;
import com.assignment.IdentityReconciliation.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/identify")
    public ResponseEntity<ContactResponseDto> identify(@RequestBody ContactRequestDto request) {
        ContactResponseDto response = contactService.identify(request.getEmail(), request.getPhoneNumber());
        return ResponseEntity.ok(response);
    }
}
