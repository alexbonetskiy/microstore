package com.alexbonetskiy.itemservice.domain;

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

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class Item implements Serializable, HasId {

    @Serial
    private static final long serialVersionUID = 100L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 30)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 256)
    private String description;

    @Column(nullable = false)
    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private int quantity;


    public boolean isAvailable(int qty) {
        return (this.quantity - qty) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Item other))
            return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
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


    @Override
    public boolean isNew() {
        return id != null;
    }
}
