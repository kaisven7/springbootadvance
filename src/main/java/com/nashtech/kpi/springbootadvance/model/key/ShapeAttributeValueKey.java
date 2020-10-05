package com.nashtech.kpi.springbootadvance.model.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShapeAttributeValueKey implements Serializable {

    private String attributeName;
    private long shape;
}
