package com.arnoldgalovics.blog.hibernateencryptionlistener.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneServiceTest {

    @Autowired
    private PhoneService phoneService;

    @After
    public void tearDown() {
        QueryCountHolder.clear();
    }

    @Test
    public void hello() {
        String phoneNumber = "010-1111-2222";
        UUID uuid = phoneService.create(phoneNumber);
        QueryCountHolder.clear();

        log.info("======== 아래에서 get만 했음에도 dirty checking에 의해 update가 발생하는지 확인 ========");
        Phone phone = phoneService.get(uuid);
        assertThat(phone.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(QueryCountHolder.getGrandTotal().getSelect()).isEqualTo(1);
        assertThat(QueryCountHolder.getGrandTotal().getUpdate()).isEqualTo(0);
    }

}