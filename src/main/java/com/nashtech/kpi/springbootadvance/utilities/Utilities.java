package com.nashtech.kpi.springbootadvance.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Utilities {

    public static Object checkValue(Object oldValue, Object newValue) {
        if(newValue == null || newValue.equals("")) {
            return oldValue.toString();
        } else {
            return newValue.toString();
        }
    }

    public static String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
