package com.nashtech.kpi.springbootadvance.entity;

import com.nashtech.kpi.springbootadvance.model.key.ShapeAttributeValueKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="SHAPE_ATTRIBUTE_VALUE")
@Data
@IdClass(ShapeAttributeValueKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShapeAttributeValue {

    @Id
    private String attributeName;

    private Double value;


    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(insertable = false, updatable = false)
    private Shape shape;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeAttributeValue that = (ShapeAttributeValue) o;
        return Objects.equals(attributeName, that.attributeName) &&
                Objects.equals(shape.getId(), that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName, shape);
    }
}
