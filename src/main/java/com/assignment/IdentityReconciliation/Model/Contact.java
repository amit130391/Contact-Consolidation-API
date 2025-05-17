package com.assignment.IdentityReconciliation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;

    private String email;

    private int linkedId;

    private String linkPrecedence;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime updatedAt;

    @Column(columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime deletedAt;
}
