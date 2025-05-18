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
    private Integer id;

    private String phoneNumber;

    private String email;

    private Integer linkedId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LinkPrecedence linkPrecedence;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime updatedAt;

    @Column(columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime deletedAt;
}
