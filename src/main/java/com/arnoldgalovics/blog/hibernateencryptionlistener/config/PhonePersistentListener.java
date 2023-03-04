package com.arnoldgalovics.blog.hibernateencryptionlistener.config;

import com.arnoldgalovics.blog.hibernateencryptionlistener.domain.Phone;
import com.arnoldgalovics.blog.hibernateencryptionlistener.encryption.Decrypter;
import com.arnoldgalovics.blog.hibernateencryptionlistener.encryption.Encrypter;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhonePersistentListener {

    private final Encrypter encrypter;
    private final Decrypter decrypter;

    @PrePersist
    public void encryptOnPersist(Phone phone) {
        String encryptedPhoneNumber = encrypter.encrypt(phone.getPhoneNumber());
        phone.setPhoneNumber(encryptedPhoneNumber);
    }

//    이부분은 주석 해제하면, updaet가 발생되면서, phone number를 다시 암호화 해버리면서 트랜잭션 밖에서는 테스트가 실패한다.
//    @PreUpdate
//    public void encryptOnUpdate(Phone phone) {
//        String encryptedPhoneNumber = encrypter.encrypt(phone.getPhoneNumber());
//        phone.setPhoneNumber(encryptedPhoneNumber);
//    }

    @PostLoad
    public void decryptOnLoad(Phone phone) {
        String decrypted = decrypter.decrypt(phone.getPhoneNumber());
        phone.setPhoneNumber(decrypted);
    }
}
