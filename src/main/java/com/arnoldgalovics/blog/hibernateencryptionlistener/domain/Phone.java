package com.arnoldgalovics.blog.hibernateencryptionlistener.domain;

import com.arnoldgalovics.blog.hibernateencryptionlistener.encryption.Encrypted;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
import lombok.ToString;

@Entity
@ToString
public class Phone {
    @Id
    private UUID id;

    @Column(name = "phone_number")
    @Encrypted
    private String phoneNumber;

    protected Phone() {
    }

    public Phone(String phoneNumber) {
        this.id = UUID.randomUUID();
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
