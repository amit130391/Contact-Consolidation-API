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

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(Integer linkedId) {
        this.linkedId = linkedId;
    }

    public LinkPrecedence getLinkPrecedence() {
        return linkPrecedence;
    }

    public void setLinkPrecedence(LinkPrecedence linkPrecedence) {
        this.linkPrecedence = linkPrecedence;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
