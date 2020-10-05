package com.nashtech.kpi.springbootadvance.entity;

import com.nashtech.kpi.springbootadvance.model.key.ShapeAttributeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name="SHAPE_ATTRIBUTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ShapeAttributeKey.class)
public class ShapeAttribute {

    @Id
    @Column(name = "ATTRIBUTE_NAME")
    @NotNull
    @Size(min = 1, max = 5)
    private String attributeName;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, insertable = false)
    private ShapeCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeAttribute that = (ShapeAttribute) o;
        return Objects.equals(attributeName, that.attributeName) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName, category);
    }
}
