package com.arnoldgalovics.blog.hibernateencryptionlistener.domain;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Transactional
    public UUID create(String phoneNumber) {
        return phoneRepository.save(new Phone(phoneNumber)).getId();
    }

    @Transactional
    public Phone get(UUID uuid) {
        return phoneRepository.findById(uuid).orElseThrow(IllegalArgumentException::new);
    }
}

