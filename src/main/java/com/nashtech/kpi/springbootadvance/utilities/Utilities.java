package com.nashtech.kpi.springbootadvance.utilities;

import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;

@Component
public class Utilities {

    public Object checkValue(Object oldValue, Object newValue) {
        if(newValue == null || newValue.equals("")) {
            return oldValue.toString();
        } else {
            return newValue.toString();
        }
    }
}
