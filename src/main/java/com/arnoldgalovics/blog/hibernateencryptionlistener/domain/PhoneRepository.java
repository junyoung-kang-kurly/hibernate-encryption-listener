package com.arnoldgalovics.blog.hibernateencryptionlistener.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {

}
