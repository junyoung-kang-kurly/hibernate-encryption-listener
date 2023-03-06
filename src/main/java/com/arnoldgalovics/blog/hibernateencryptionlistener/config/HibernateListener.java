package com.arnoldgalovics.blog.hibernateencryptionlistener.config;

import com.arnoldgalovics.blog.hibernateencryptionlistener.encryption.EncryptionListener;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HibernateListener {

    private final EntityManagerFactory entityManagerFactory;
    private final EncryptionListener encryptionListener;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_LOAD).appendListener(encryptionListener);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(encryptionListener);
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(encryptionListener);
    }
}