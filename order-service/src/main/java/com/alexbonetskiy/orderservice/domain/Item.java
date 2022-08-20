package com.alexbonetskiy.orderservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @Id
    private Integer id;

    @Column(nullable = false, updatable = false)
    @NotBlank
    @Size(max = 30)
    private String name;

    @Column(nullable = false, updatable = false)
    @NotBlank
    @Size(max = 256)
    private String description;

    @Column(nullable = false, updatable = false)
    @NotNull
    @Positive
    private BigDecimal price;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<OrderItem> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Item other))
            return false;

        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }



}
