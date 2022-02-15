package com.jasfair.springsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encode = pe.encode("123");
        System.out.println(encode);
        System.out.println(pe.matches("123", encode));
    }

}
