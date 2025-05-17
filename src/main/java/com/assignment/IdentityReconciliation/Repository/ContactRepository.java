package com.assignment.IdentityReconciliation.Repository;

import com.assignment.IdentityReconciliation.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
