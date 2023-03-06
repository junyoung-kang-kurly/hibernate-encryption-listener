package com.arnoldgalovics.blog.hibernateencryptionlistener.encryption;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class FieldDecrypter {
    @Autowired
    private Decrypter decrypter;

    public void decrypt(Object[] state, String[] propertyNames, Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            // todo. 혹시 여기서 다른방식으로 state를 참조할수 있나 삽질중 T.T 안될거같다. 좀더 해보고 리버트 하자.
            field.setAccessible(true);
            Object o = field.get(entity);
            decryptField(field, state, propertyNames);
        }, EncryptionUtils::isFieldEncrypted);
    }

    private void decryptField(Field field, Object[] state, String[] propertyNames) {
        int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[propertyIndex];  // 에러 [Cannot load from object array because "state" is null] 발생
        if (currentValue != null) {
            if (!(currentValue instanceof String)) {
                throw new IllegalStateException("Encrypted annotation was used on a non-String field");
            }
            state[propertyIndex] = decrypter.decrypt(currentValue.toString());
        }
    }
}
