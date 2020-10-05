package com.nashtech.kpi.springbootadvance.model.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShapeAttributeKey implements Serializable {

    private String attributeName;
    private long category;

}
