package com.alexbonetskiy.itemservice.domain;

import com.alexbonetskiy.itemservice.utils.BigDecimal2JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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
    @JsonDeserialize(using = BigDecimal2JsonDeserializer.class)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private int quantity;


    public boolean isAvailable(int qty) {
        return (this.quantity - qty) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id.equals(item.id) && name.equals(item.name) && description.equals(item.description) && price.equals(item.price);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price);
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
        return id == null;
    }
}
