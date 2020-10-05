package com.nashtech.kpi.springbootadvance.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="SHAPE")
public class Shape {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn( name="CAT_ID", referencedColumnName = "ID")
    private ShapeCategory category;

    @Column(name = "OWNER")
    private String user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "shape_id")
    private Set<ShapeAttributeValue> attributes = new HashSet<>();

    public Map<String, Double> getAttributesMap() {
        Map<String, Double> map = new HashMap<>();
        attributes.forEach(attr ->
                map.put(attr.getAttributeName(), attr.getValue())
        );
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return Objects.equals(id, shape.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShapeService{" +
                "id=" + id +
                ", category=" + category +
                ", user='" + user + '\'' +
                '}';
    }

}
